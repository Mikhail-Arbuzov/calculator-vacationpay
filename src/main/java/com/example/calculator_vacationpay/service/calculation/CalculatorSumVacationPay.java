package com.example.calculator_vacationpay.service.calculation;

import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
public class CalculatorSumVacationPay implements ICalculateSumVacationPay {
    private final String MONTH = "12";
    // константа количества дней расчетного периода
    private final double QUANTITY_OF_DAYS_CALCULATION_PERIOD = 29.3;

    private CalculatorSumVacationPay() {
    }

    /* Cумма отпускных рассчитывается по следующим формулам:
     * средняя сумма зарплаты за месяц = средняя сумма зарплаты за 12 месяцев / 12
     * (результат округляется до двух знаков после запятой)
     * средняя сумма зарплаты за день = средняя сумма зарплаты за месяц / количество дней расчетного периода
     * (результат округляется до двух знаков после запятой)
     * сумма отпускных = средняя сумма зарплаты за день * количество дней отпуска
     * */
    @Override
    public BigDecimal getSumVacationPay(BigDecimal averageSalaryFor12Months, int numberOfVacationDays) {
        BigDecimal averageSalaryForMonth
                = averageSalaryFor12Months.divide(new BigDecimal(MONTH), 2, RoundingMode.HALF_UP);
        BigDecimal quantityOfDays = BigDecimal.valueOf(QUANTITY_OF_DAYS_CALCULATION_PERIOD);
        BigDecimal averageSalaryForDay = averageSalaryForMonth.divide(quantityOfDays, 2, RoundingMode.HALF_UP);
        BigDecimal vacationDays = new BigDecimal(String.valueOf(numberOfVacationDays));
        return averageSalaryForDay.multiply(vacationDays);
    }

    //реализация паттерна singleton с использованием внутреннего класса
    private static class CalculatorSumVacationPayHolder {
        private final static CalculatorSumVacationPay calculator = new CalculatorSumVacationPay();
    }

    //объект инициализируется при первом вызове метода getInstance()
    public static CalculatorSumVacationPay getInstance() {
        return CalculatorSumVacationPayHolder.calculator;
    }

}
