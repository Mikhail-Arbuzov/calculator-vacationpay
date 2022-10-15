package com.example.calculator_vacationpay.service;

import com.example.calculator_vacationpay.service.calculation.ICalculateSumVacationPay;
import com.example.calculator_vacationpay.service.creators.CreatorCalculateSumVacationPay;
import com.example.calculator_vacationpay.service.creators.ICreatorCalculateSumVacationPay;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/* Класс создающий объект сервес,
 * в котором выполняется логика расчета суммы отпускных
 * на основании суммы средней зарплаты за 12 месяцев и количества дней отпуска.*/
@Service
public class CalculationServiceVacationPay {
    private final ICreatorCalculateSumVacationPay creatorCalculateSumVacationPay
            = new CreatorCalculateSumVacationPay();

    //метод возвращает информацию о результате расчета суммы отпускных
    public String getInfoSumVacationPay(BigDecimal averageSalaryFor12Months,
                                        int numberOfVacationDays) {
        ICalculateSumVacationPay calculatorSumVacationPay
                = creatorCalculateSumVacationPay.createCalculateSumVacationPay();
        BigDecimal sumVacationPay
                = calculatorSumVacationPay.getSumVacationPay(averageSalaryFor12Months, numberOfVacationDays);
        return "Сумма отпускных: " + sumVacationPay + " руб.";
    }
}
