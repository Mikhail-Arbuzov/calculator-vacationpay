package com.example.calculator_vacationpay.service.customerror;

/* Класс создающий объект исключения,
  возникающего при указании параметра даты,
  которая не существует */
public class NotExistingDateException extends ParameterException{
    public NotExistingDateException(String message) {
        super(message);
    }
}
