package com.example.calculator_vacationpay.service.customerror;

/* Класс создающий объект исключения,
 * возникающего при указании неверных параметров запроса.
 * Например, неверное количество переданных парамметров
 * или параметр суммы средней зарплаты за 12 месяцев
 * был передан ввиде числа меньше или равному нулю.*/
public class InvalidRequestParametersException extends ParameterException {
    public InvalidRequestParametersException(String message) {
        super(message);
    }
}
