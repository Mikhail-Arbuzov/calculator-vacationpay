package com.example.calculator_vacationpay.service.calculation;

import java.math.BigDecimal;

public interface ICalculateSumVacationPay {
    //метод должен вернуть сумму отпускных
    BigDecimal getSumVacationPay(BigDecimal averageSalaryFor12Months, int numberOfVacationDays);
}
