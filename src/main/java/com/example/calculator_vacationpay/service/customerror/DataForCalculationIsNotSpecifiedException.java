package com.example.calculator_vacationpay.service.customerror;

/* Класс создающий объект исключения,
 * возникающего при не указании не единого параметра запроса,
 * связи с чем данные для расчетов отсутствуют */
public class DataForCalculationIsNotSpecifiedException extends ParameterException{
    public DataForCalculationIsNotSpecifiedException(String message) {
        super(message);
    }
}
