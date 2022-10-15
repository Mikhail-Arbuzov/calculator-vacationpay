package com.example.calculator_vacationpay.service.calculation;

import com.example.calculator_vacationpay.service.creators.CreatorCalculateSumVacationPay;
import com.example.calculator_vacationpay.service.creators.ICreatorCalculateSumVacationPay;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorSumVacationPayTest {
    private static ICreatorCalculateSumVacationPay creatorCalculateSumVacationPay
            = new CreatorCalculateSumVacationPay();

    private static ICalculateSumVacationPay calculateSumVacationPay;

    @BeforeAll
    public static void initialCalculatorSumVacationPay(){
        calculateSumVacationPay = creatorCalculateSumVacationPay.createCalculateSumVacationPay();
    }

    @Test
    public void methodShouldReturnSumVacationPay(){
        BigDecimal averageSalaryFor12Month = new BigDecimal("160948.64");
        int numberOfVacationDays = 13;
        BigDecimal expectedResult = new BigDecimal("5950.88");

        /* Сумма отпускных определяется следующим образом
         * Средняя зарплата за месяц = 160948.64 / 12 (результат округляется до двух знаков после запятой)
         * Средняя зарплата за день = средняя зарплата за месяц / 29.3 (13412.39 / 29.3)
         * (результат округляется до двух знаков после запятой - 457.76)
         * Сумма отпускных = 457.76 * 13 (результат 5950.88)
         * Таким образом метод getSumVacationPay(), должен вернуть ожидаемый результат- 5950.88
         * */
        assertEquals(expectedResult,
                calculateSumVacationPay.getSumVacationPay(averageSalaryFor12Month, numberOfVacationDays));
    }

}