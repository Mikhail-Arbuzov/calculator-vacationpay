package com.example.calculator_vacationpay.service.check;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckingDateValues implements ICheckDateValues {
    private CheckingDateValues() {
    }

    @Override
    public boolean isExistDates(String dateStart, String dateEnd) {
        //переданные строки, представляю ввиде массива чисел, разеленных по точке
        String[] arrayDateStart = dateStart.split("\\.");
        String[] arrayDateEnd = dateEnd.split("\\.");
        //для каждой даты создаю переменную хранящию результат проверки даты,
        // которая передается ввиде чисел дня,месяца и года
        boolean checkResult1 = isCorrectDate(Integer.parseInt(arrayDateStart[0]),
                Integer.parseInt(arrayDateStart[1]),
                Integer.parseInt(arrayDateStart[2]));
        boolean checkResult2 = isCorrectDate(Integer.parseInt(arrayDateEnd[0]),
                Integer.parseInt(arrayDateEnd[1]),
                Integer.parseInt(arrayDateEnd[2]));

        return checkResult1 && checkResult2;
    }

    @Override
    public boolean isCorrectFormatDates(String dateStart, String dateEnd) {
        //переданные строки должны соответствовать данному формату
        String pattern = "^(\\d{2})\\.(\\d{2})\\.(\\d{4})$";
        Pattern template = Pattern.compile(pattern);
        Matcher matchDateStart = template.matcher(dateStart);
        Matcher matchDateEnd = template.matcher(dateEnd);
        return matchDateStart.matches() && matchDateEnd.matches();
    }

    @Override
    public boolean isBeforeDateEnd(String startDate, String endDate) {
        LocalDate dateStart = getDateConvertedFromString(startDate);
        LocalDate dateEnd = getDateConvertedFromString(endDate);
        return dateStart.isBefore(dateEnd);
    }

    @Override
    public boolean isEqualsDates(String dateStart, String dateEnd) {
        LocalDate startDate = getDateConvertedFromString(dateStart);
        LocalDate endDate = getDateConvertedFromString(dateEnd);
        return startDate.isEqual(endDate);
    }

    //реализация паттерна singleton с использованием внутреннего класса
    private static class CheckingDateValuesHolder {
        private final static CheckingDateValues checkingDateValues = new CheckingDateValues();
    }

    //объект инициализируется при первом вызове метода getInstance()
    public static CheckingDateValues getInstance() {
        return CheckingDateValuesHolder.checkingDateValues;
    }

    //метод для преобразования строки в дату
    private LocalDate getDateConvertedFromString(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    //метод для проверки верно указанной даты
    private boolean isCorrectDate(int day, int month, int year) {
        boolean flag = false;
        //проверка на корректность указанного месяца и года
        boolean check = ((month > 0 && month <= 12) && year > 1970);
        //проверка на то,что является ли год високосным
        boolean leapYear = ((year % 400 == 0) || (year % 4 == 0 && year % 100 != 0));
        //проверка на корректность указанной даты с учетом кол-во дней в месяцах
        if (check) {
            switch (month) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    if (day > 0 && day <= 31) {
                        flag = true;
                    }
                    break;
                case 2:
                    if (leapYear) {
                        if (day > 0 && day <= 29) flag = true;
                    } else {
                        if (day > 0 && day <= 28) flag = true;
                    }
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    if (day > 0 && day <= 30) {
                        flag = true;
                    }
                    break;
            }
        }

        return flag;
    }

}
