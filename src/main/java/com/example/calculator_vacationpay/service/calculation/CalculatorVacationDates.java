package com.example.calculator_vacationpay.service.calculation;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

public class CalculatorVacationDates implements ICalculateNumberOfVacationDays {
    private CalculatorVacationDates() {
    }

    @Override
    public int getNumberOfVacationDays(LocalDate dateStart, LocalDate dateEnd) {
        int numberOfHolidays = calculateOfHolidaysInMonth(dateStart, dateEnd);
        int result = 0;
        if (numberOfHolidays > 0) {
            //если период отпуска попадает на праздничные дни,то к дате оканчания отпуска прибавляется их кол-во
            LocalDate newDateEnd = dateEnd.plusDays(numberOfHolidays);
            int numberOfDaysBetweenDates = calculateNumberOfDaysBetweenDates(dateStart, newDateEnd);
            //количество дней отпуска с учетом праздников
            result = numberOfDaysBetweenDates - numberOfHolidays;
        } else {
            result = calculateNumberOfDaysBetweenDates(dateStart, dateEnd);
        }
        return result;
    }

    //реализация паттерна singleton с использованием внутреннего класса
    private static class CalculatorVacationDatesHolder {
        private final static CalculatorVacationDates calculatorVacationDates = new CalculatorVacationDates();
    }

    //объект инициализируется при первом вызове метода getInstance()
    public static CalculatorVacationDates getInstance() {
        return CalculatorVacationDatesHolder.calculatorVacationDates;
    }

    // метод для подсчета количества праздничных дней в месяце
    private int calculateOfHolidaysInMonth(LocalDate startDate, LocalDate endDate) {
        int countHolidays = 0;
        List<Integer> januaryHolidays = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        List<Integer> mayHolidays = Arrays.asList(1, 9);
        //подсчет кол-во праздничных дней в месяце в зависимоти от указанного промежутка дат
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            switch (date.getMonth()) {
                case JANUARY:
                    for (Integer day : januaryHolidays) {
                        if (day == date.getDayOfMonth()) {
                            countHolidays++;
                        }
                    }
                    break;
                case FEBRUARY:
                    if (date.getDayOfMonth() == 23) {
                        countHolidays++;
                    }
                    break;
                case MARCH:
                    if (date.getDayOfMonth() == 8) {
                        countHolidays++;
                    }
                    break;
                case MAY:
                    for (Integer d : mayHolidays) {
                        if (d == date.getDayOfMonth()) {
                            countHolidays++;
                        }
                    }
                    break;
                case JUNE:
                    if (date.getDayOfMonth() == 12) {
                        countHolidays++;
                    }
                    break;
                case NOVEMBER:
                    if (date.getDayOfMonth() == 4) {
                        countHolidays++;
                    }
                    break;
            }
        }

        return countHolidays;
    }

    // метод для расчета количества дней между указаннами датами
    private int calculateNumberOfDaysBetweenDates(LocalDate startDate, LocalDate endDate) {
        //не включает в расчет дату окончания отпуска
        long numberOfDays = startDate.until(endDate, ChronoUnit.DAYS);
        //результат расчета количества дней между датами отпуска плюс дата окончания отпуска
        int result = Long.valueOf(numberOfDays).intValue() + 1;
        return result;
    }
}
