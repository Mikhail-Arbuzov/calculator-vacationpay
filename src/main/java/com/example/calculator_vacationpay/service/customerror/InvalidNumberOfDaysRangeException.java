package com.example.calculator_vacationpay.service.customerror;

/* Класс создающий объект исключения,
  возникающего при указании неверного диапазона числа
  количества дней отпуска в параметре запроса */
public class InvalidNumberOfDaysRangeException extends ParameterException {
    public InvalidNumberOfDaysRangeException(String message) {
        super(message);
    }
}
