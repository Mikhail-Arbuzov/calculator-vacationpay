package com.example.calculator_vacationpay.service.calculation;

import java.time.LocalDate;

public interface ICalculateNumberOfVacationDays {
    //метод возвращает количество дней отпуска за указанный период
    int getNumberOfVacationDays(LocalDate dateStart, LocalDate dateEnd);
}
