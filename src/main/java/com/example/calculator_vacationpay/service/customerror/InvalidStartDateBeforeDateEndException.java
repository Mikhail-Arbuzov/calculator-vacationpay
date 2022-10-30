package com.example.calculator_vacationpay.service.customerror;

/* Класс создающий объект исключения,
  возникающего при указании параметра даты начала отпуска,
  которая может оказаться познее даты окончания отпуска  */
public class InvalidStartDateBeforeDateEndException extends ParameterException{
    public InvalidStartDateBeforeDateEndException(String message) {
        super(message);
    }
}
