package com.example.calculator_vacationpay.service.check;

public interface ICheckDateValues {
    //метод для проверки существования дат
    boolean isExistDates(String dateStart, String dateEnd);
    // метод для проверки формата дат
    boolean isCorrectFormatDates(String dateStart, String dateEnd);
    // метод для проверки, что начальная дата расположена раньше конечной даты
    boolean isBeforeDateEnd(String startDate, String endDate);
    // метод для проверки, что переданные даты равны
    boolean isEqualsDates(String dateStart, String dateEnd);
}
