package com.example.calculator_vacationpay.service;

import com.example.calculator_vacationpay.service.calculation.ICalculateNumberOfVacationDays;
import com.example.calculator_vacationpay.service.calculation.ICalculateSumVacationPay;
import com.example.calculator_vacationpay.service.creators.CreatorCalculateNumberOfVacationDays;
import com.example.calculator_vacationpay.service.creators.CreatorCalculateSumVacationPay;
import com.example.calculator_vacationpay.service.creators.ICreatorCalculateNumberOfVacationDays;
import com.example.calculator_vacationpay.service.creators.ICreatorCalculateSumVacationPay;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/* Класс создающий объект сервес,
 * в котором выполняется логика расчета суммы отпускных
 * на основании суммы средней зарплаты за 12 месяцев и точных дней ухода в отпуск*/
@Service
public class CalculationServiceVacationPayForConcreteDates {
    private final ICreatorCalculateNumberOfVacationDays creatorCalculateNumberOfVacationDays
            = new CreatorCalculateNumberOfVacationDays();
    private final ICreatorCalculateSumVacationPay creatorCalculateSumVacationPay
            = new CreatorCalculateSumVacationPay();

    //метод возвращает информацию о результате расчета суммы отпускных за конкретные даты
    public String getInfoSumVacationPayForConcreteDates(BigDecimal averageSalaryFor12Months,
                                                        String dateStart,
                                                        String dateEnd) {
        //Инициализация объекта для расчета кол-ва дней отпуска
        ICalculateNumberOfVacationDays calculateNumberOfVacationDays
                = creatorCalculateNumberOfVacationDays.createCalculateNumberOfVacationDays();
        //Инициализация объекта для расчета суммы отпускных
        ICalculateSumVacationPay calculateSumVacationPay
                = creatorCalculateSumVacationPay.createCalculateSumVacationPay();
        //преобразование строк в даты
        LocalDate startDate = LocalDate.parse(dateStart, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        LocalDate endDate = LocalDate.parse(dateEnd, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        //расчет
        int numberOfVacationDays = calculateNumberOfVacationDays.getNumberOfVacationDays(startDate, endDate);
        BigDecimal sumVacationPay
                = calculateSumVacationPay.getSumVacationPay(averageSalaryFor12Months, numberOfVacationDays);
        return "Количество дней отпуска: " + numberOfVacationDays +
                " \nСумма отпускных с учетом кол-во дней отпуска: " + sumVacationPay + " руб.";
    }
}
