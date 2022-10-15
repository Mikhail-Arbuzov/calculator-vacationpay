package com.example.calculator_vacationpay.service.customerror;

/* Базовый класс исключений,
* возникающих при указании неверных
* параметров в запросе */

public class ParameterException extends Exception {
    public ParameterException (String message){
        super(message);
    }
}
