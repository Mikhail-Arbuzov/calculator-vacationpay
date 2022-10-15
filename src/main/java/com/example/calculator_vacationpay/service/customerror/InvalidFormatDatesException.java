package com.example.calculator_vacationpay.service.customerror;

/* Класс создающий объект исключения,
 * возникающего при указании
 * неверного формата дат в запросе */
public class InvalidFormatDatesException extends ParameterException{
    public InvalidFormatDatesException(String message) {
        super(message);
    }
}
