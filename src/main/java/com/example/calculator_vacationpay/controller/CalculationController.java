package com.example.calculator_vacationpay.controller;

import com.example.calculator_vacationpay.service.CalculationServiceVacationPay;
import com.example.calculator_vacationpay.service.CalculationServiceVacationPayForConcreteDates;
import com.example.calculator_vacationpay.service.check.ICheckDateValues;
import com.example.calculator_vacationpay.service.creators.CreatorCheckDateValues;
import com.example.calculator_vacationpay.service.creators.ICreatorCheckDateValues;
import com.example.calculator_vacationpay.service.customerror.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.math.BigDecimal;

@RestController
@AllArgsConstructor
public class CalculationController {
    private final CalculationServiceVacationPay serviceVacationPay;
    private final CalculationServiceVacationPayForConcreteDates serviceVacationPayForConcreteDates;
    private final ICreatorCheckDateValues creatorCheckDateValues = new CreatorCheckDateValues();

    @GetMapping("/calculacte")
    public ResponseEntity<String> getInfoSumVacationPay(
            @RequestParam(name = "salary", defaultValue = "0") BigDecimal salary,
            @RequestParam(name = "number", defaultValue = "0") int number,
            @RequestParam(name = "dateStart", defaultValue = "") String dateStart,
            @RequestParam(name = "dateEnd", defaultValue = "") String dateEnd) throws ParameterException {

        String info = "";
        if (salary.compareTo(BigDecimal.ZERO) == 0 && number == 0 && dateStart.isEmpty() && dateEnd.isEmpty()){
            String message = "Для получения информации о сумме отпускных, нужно указать параметры в конце" +
                    " URL-адреса после знака \"?\". " +
                    "\nУказываются следующие параметры - salary,number ." +
                    "\nГде salary - средняя зарплата за 12 месяцев;" +
                    "\nnumber - количество дней отпуска." +
                    "\nЕсли необходимо узнать сумму отпускных с учетом точных дней ухода в отпуск," +
                    " то тогда необходимо указать следующие параметры: " +
                    "\nsalary - средняя зарплата за 12 месяцев;" +
                    "\ndateStart - дата начала отпуска;" +
                    "\ndateEnd - дата окончания отпуска." +
                    "\nПри этом даты нужно указать в определенном формате(02.05.2022).";
            throw new DataForCalculationIsNotSpecifiedException(message);
        }
        else if(salary.compareTo(BigDecimal.ZERO) > 0 && number == 0 && !dateStart.isEmpty() && !dateEnd.isEmpty()){
            checkValuesDates(dateStart,dateEnd);
            info = serviceVacationPayForConcreteDates.getInfoSumVacationPayForConcreteDates(salary,dateStart,dateEnd);
        }
        else if (salary.compareTo(BigDecimal.ZERO) > 0 && number > 0 && dateStart.isEmpty() && dateEnd.isEmpty()){
            checkValuesNumber(number);
            info = serviceVacationPay.getInfoSumVacationPay(salary,number);
        }
        else {
            throw new InvalidRequestParametersException("Неверно указаны параметры запроса.");
        }
        return new ResponseEntity<>(info, HttpStatus.OK);
    }

    //метод для проверки параметров dateStart и dateEnd
    private void checkValuesDates(String dateStart, String dateEnd) throws ParameterException {
        ICheckDateValues checkDateValues = creatorCheckDateValues.createCheckDateValues();

        if(!checkDateValues.isCorrectFormatDates(dateStart,dateEnd)){
            String message = "Неверно введен формат даты отпуска.Пример верного формата (02.05.2022).";
            throw new InvalidFormatDatesException(message);
        }
        else{

            if(!checkDateValues.isExistDates(dateStart,dateEnd)){
                throw new NotExistingDateException("Указанная дата не существует.");
            }
            if(!checkDateValues.isBeforeDateEnd(dateStart,dateEnd) &&
                    !checkDateValues.isEqualsDates(dateStart,dateEnd)){
                String infoResult = "Указанная дата начала отпуска оказалась позже даты оканчания отпуска.";
                throw new InvalidStartDateBeforeDateEndException(infoResult);
            }
        }
    }

    //метод для проверки параметра number
    private void checkValuesNumber(int number) throws ParameterException{
        if(!(number > 0 && number <= 56)){
            String result = "Число количества дней отпуска должно быть указано," +
                    " в приделах диапазона от 0 до 56.";
            throw new InvalidNumberOfDaysRangeException(result);
        }
    }

    //обработка исключений

    @ExceptionHandler(ParameterException.class)
    public ResponseEntity<String> handleParameterException(ParameterException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleMethodArgumentTypeMismatchException(){
        String message = "Значения параметера salary и number должно быть числом.";
        return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
    }
}
