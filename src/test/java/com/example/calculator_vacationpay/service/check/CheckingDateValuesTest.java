package com.example.calculator_vacationpay.service.check;

import com.example.calculator_vacationpay.service.creators.CreatorCheckDateValues;
import com.example.calculator_vacationpay.service.creators.ICreatorCheckDateValues;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class CheckingDateValuesTest {
    private static ICreatorCheckDateValues creatorCheckDateValues = new CreatorCheckDateValues();
    private static ICheckDateValues checkDateValues;
    private static Random random;

    @BeforeAll
    public static void initialCheckingDateValues() {
        checkDateValues = creatorCheckDateValues.createCheckDateValues();
        random = new Random();
    }

    // Проверка различных вариантов данных переданных в метод isExistDates()

    @Test
    public void datesShouldNotExist() {
        int day1 = 0;
        int day2 = 0;
        int month = 0;
        int year = 0;
        StringBuffer dateStart = new StringBuffer();
        StringBuffer dateEnd = new StringBuffer();
        for (int i = 0; i <= 1000; i++) {
            //чтобы максимальное число включить в расчет нужно прибавить 1
            day1 = random.nextInt((99 - 32) + 1) + 32; // начальная дата берется в диапазоне от 32 до 99
            day2 = random.nextInt((99 - 1) + 1) + 1; // конечная дата берется в диапазоне от 1 до 99
            month = random.nextInt((99 - 1) + 1) + 1;// месяц берется в диапазоне от 1 до 99
            year = random.nextInt((2040 - 1900) + 1) + 1900; // год берется в диапазоне от 1900 до 2040

            // несуществующие даты формируются в строку
            dateStart.append(day1).append(".").append(month).append(".").append(year);
            dateEnd.append(day2).append(".").append(month).append(".").append(year);

            assertFalse(checkDateValues.isExistDates(String.valueOf(dateStart), String.valueOf(dateEnd)));
            //делаю замену на пустую строку, чтобы следующая дата не склеивалась к предыдущей
            dateStart.replace(0, 10, "");
            dateEnd.replace(0, 10, "");
        }
    }

    //из 10 запусков 1 раз тест заваливался, в остальных случаях проходит
    @Test
    public void datesShouldExist() {
        int dayApril = 0;
        int dayMay = 0;
        int year = 0;
        StringBuffer date1 = new StringBuffer();
        StringBuffer date2 = new StringBuffer();
        for (int i = 0; i <= 1000; i++) {
            // в апреле даты меняются с 1 по 30 число
            dayApril = 1 + random.nextInt((30 - 1) + 1);
            //в мае даты меняются с 1 по 31 число
            dayMay = 1 + random.nextInt((31 - 1) + 1);
            // год берется в диапозоне от 1970 по 9999,так как должен быть больше 1970
            year = 1970 + random.nextInt((9999 - 1970) + 1);

            date1.append(dayApril).append(".").append("04").append(".").append(year);
            date2.append(dayMay).append(".").append("05").append(".").append(year);

            assertTrue(checkDateValues.isExistDates(String.valueOf(date1), String.valueOf(date2)));
            //делаю замену на пустую строку, чтобы следующая дата не склеивалась к предыдущей
            date1.replace(0, 10, "");
            date2.replace(0, 10, "");
        }
    }

    @Test
    public void monthShouldNotExist() {
        int day = 0;
        int month = 0;
        int year = 0;
        StringBuffer dateStart = new StringBuffer();
        StringBuffer dateEnd = new StringBuffer();
        for (int i = 0; i <= 1000; i++) {
            day = random.nextInt((30 - 1) + 1) + 1;
            month = random.nextInt((99 - 13) + 1) + 13; // месяц берется в диапазоне от 13 - 99
            year = random.nextInt((2022 - 1970) + 1) + 1970;

            //дата берется с учетом, того что в месяце должно быть 30 дней(к примеру месяц сентябрь)
            dateStart.append(day).append(".").append(9).append(".").append(year);

            dateEnd.append(day).append(".").append(month).append(".").append(year);

            assertFalse(checkDateValues.isExistDates(String.valueOf(dateStart), String.valueOf(dateEnd)));
            //делаю замену на пустую строку, чтобы следующая дата не склеивалась к предыдущей
            dateStart.replace(0, 10, "");
            dateEnd.replace(0, 10, "");
        }
        //если число месяца - 0
        assertFalse(checkDateValues.isExistDates("21.10.2002", "03.00.2002"));
    }

    @Test
    public void leapYearShouldBeCorrect() {
        int day = 0;
        int year = 0;
        StringBuffer dateStart = new StringBuffer();
        StringBuffer dateEnd = new StringBuffer();
        for (int i = 0; i <= 1000; i++) {
            day = 1 + random.nextInt((31 - 1) + 1);
            year = 2000 + random.nextInt((2060 - 2000) + 1); //год в диапазоне от 2000 до 2050
            if (year % 4 == 0) {
                //дата должна быть в високосном году
                dateStart.append(29).append(".").append("02").append(".").append(year);
                //дата берется с учетом, того что в месяце должен быть 31 день(к примеру месяц март)
                dateEnd.append(day).append(".").append("03").append(".").append(year);
                assertTrue(checkDateValues.isExistDates(String.valueOf(dateStart), String.valueOf(dateEnd)));
                //делаю замену на пустую строку, чтобы следующая дата не склеивалась к предыдущей
                dateStart.replace(0, 10, "");
                dateEnd.replace(0, 10, "");
            } else {
                continue;
            }
        }
    }

    @Test
    public void notLeapYearShouldBeCorrect() {
        int day = 0;
        int year = 0;
        StringBuffer dateStart = new StringBuffer();
        StringBuffer dateEnd = new StringBuffer();
        for (int i = 0; i <= 1000; i++) {
            day = 1 + random.nextInt((28 - 1) + 1);
            year = 2000 + random.nextInt((2030 - 2000) + 1);
            if (year % 4 != 0) {
                //дата берется с учетом, того что в месяце должно быть 28 дней(февраль)
                dateStart.append(day).append(".").append(2).append(".").append(year);
                dateEnd.append("28").append(".").append("02").append(".").append(year);
                assertTrue(checkDateValues.isExistDates(String.valueOf(dateStart), String.valueOf(dateEnd)));
                //делаю замену на пустую строку, чтобы следующая дата не склеивалась к предыдущей
                dateStart.replace(0, 10, "");
                dateEnd.replace(0, 10, "");
            }
        }
    }

    // Проверка различных вариантов данных переданных в метод isCorrectFormatDates()

    @Test
    public void formatDateShouldBeNotCorrect() {
        String randomChars = "461fhlrFNd_!$>abcd-*";
        StringBuffer dateStart = new StringBuffer();
        StringBuffer dateEnd = new StringBuffer();
        int day = 0;
        int month = 0;
        int year = 0;

        for (int j = 0; j <= 1000; j++) {
            day = 1 + random.nextInt((30 - 1) + 1);
            month = 1 + random.nextInt((12 - 1) + 1);
            year = 1970 + random.nextInt((2022 - 1970) + 1);

            //создания случайной строки из символов строки randomChars
            for (int i = 0; i <= 10; i++) {
                int number = random.nextInt(20);
                dateStart.append(randomChars.charAt(number));
            }

            dateEnd.append(day).append(".").append(month).append(".").append(year);
            assertFalse(checkDateValues.isCorrectFormatDates(String.valueOf(dateStart), String.valueOf(dateEnd)));
            dateStart.replace(0, 11, "");
            dateEnd.replace(0, 10, "");
        }
    }

    @Test
    public void formatDateShouldBeCorrect() {
        int day = 0;
        int day2 = 0;
        int month = 0;
        int year = 0;
        StringBuffer dateStart = new StringBuffer();
        StringBuffer dateEnd = new StringBuffer();

        for (int i = 0; i <= 1000; i++) {
            day = 1 + random.nextInt((31 - 1) + 1);
            day2 = 1 + random.nextInt((30 - 1) + 1);
            month = 1 + random.nextInt((12 - 1) + 1);
            year = 1970 + random.nextInt((2022 - 1970) + 1);
            //условия для создания корректных строк совпадающих с шаблоном (**.**.****)
            if (day < 10 && day2 < 10 && month < 10) {
                dateStart.append("0").append(day).append(".").append("0").append(month).append(".").append(year);
                dateEnd.append("0").append(day2).append(".").append("0").append(month).append(".").append(year);
            } else if (day < 10 && day2 < 10) {
                dateStart.append("0").append(day).append(".").append(month).append(".").append(year);
                dateEnd.append("0").append(day2).append(".").append(month).append(".").append(year);
            } else if (day < 10 && month < 10) {
                dateStart.append("0").append(day).append(".").append("0").append(month).append(".").append(year);
                dateEnd.append(day2).append(".").append("0").append(month).append(".").append(year);
            } else if (day2 < 10 && month < 10) {
                dateStart.append(day).append(".").append("0").append(month).append(".").append(year);
                dateEnd.append("0").append(day2).append(".").append("0").append(month).append(".").append(year);
            } else if (month < 10) {
                dateStart.append(day).append(".").append("0").append(month).append(".").append(year);
                dateEnd.append(day2).append(".").append("0").append(month).append(".").append(year);
            } else if (day < 10) {
                dateStart.append("0").append(day).append(".").append(month).append(".").append(year);
                dateEnd.append(day2).append(".").append(month).append(".").append(year);
            } else if (day2 < 10) {
                dateStart.append(day).append(".").append(month).append(".").append(year);
                dateEnd.append("0").append(day2).append(".").append(month).append(".").append(year);
            } else {
                dateStart.append(day).append(".").append(month).append(".").append(year);
                dateEnd.append(day2).append(".").append(month).append(".").append(year);
            }

            assertTrue(checkDateValues.isCorrectFormatDates(String.valueOf(dateStart), String.valueOf(dateEnd)));
            dateStart.replace(0, 10, "");
            dateEnd.replace(0, 10, "");
        }
    }

    // Проверка различных вариантов данных переданных в метод isBeforeDateEnd()
    @Test
    public void dateStartShouldBeBeforeDateEnd() {
        int day = 0;
        int day2 = 0;
        int month = 0;
        int month2 = 0;
        int year = 0;
        StringBuffer dateStart = new StringBuffer();
        StringBuffer dateEnd = new StringBuffer();
        for (int i = 0; i <= 1000; i++) {
            day = 1 + random.nextInt((15 - 1) + 1); //первая половина месяца с 1 по 15 число
            day2 = 16 + random.nextInt((30 - 16) + 1);// вторая половина месяца с 16 по 30 число
            //месяца в которых количество дней доходят до 30
            month = 3 + random.nextInt((7 - 3) + 1);// с марта по июль
            month2 = 8 + random.nextInt((12 - 8) + 1); // с августа по декабрь
            year = 1970 + random.nextInt((2022 - 1970) + 1); //год с 1970 по 2022

            //условия для создания корректных строк совпадающих с шаблоном (**.**.****)
            if (day < 10 && month2 >= 10) {
                dateStart.append("0").append(day).append(".").append("0").append(month).append(".").append(year);
                dateEnd.append(day2).append(".").append(month2).append(".").append(year);
            } else if (month2 < 10 && day < 10) {
                dateStart.append("0").append(day).append(".").append("0").append(month).append(".").append(year);
                dateEnd.append(day2).append(".").append("0").append(month2).append(".").append(year);
            } else if (day >= 10 && month2 < 10) {
                dateStart.append(day).append(".").append("0").append(month).append(".").append(year);
                dateEnd.append(day2).append(".").append("0").append(month2).append(".").append(year);
            } else {
                dateStart.append(day).append(".").append("0").append(month).append(".").append(year);
                dateEnd.append(day2).append(".").append(month2).append(".").append(year);
            }

            assertTrue(checkDateValues.isBeforeDateEnd(String.valueOf(dateStart), String.valueOf(dateEnd)));
            dateStart.replace(0, 10, "");
            dateEnd.replace(0, 10, "");
        }
    }

    @Test
    public void dateStartShouldBeNotBeforeDateEnd() {
        int day = 0;
        int day2 = 0;
        int month = 0;
        int month2 = 0;
        int year = 0;
        StringBuffer dateStart = new StringBuffer();
        StringBuffer dateEnd = new StringBuffer();
        for (int i = 0; i <= 1000; i++) {
            day = 16 + random.nextInt((30 - 16) + 1);// вторая половина месяца с 16 по 30 число
            day2 = 1 + random.nextInt((15 - 1) + 1);//первая половина месяца с 1 по 15 число
            //месяца в которых количество дней доходят до 30
            month = 8 + random.nextInt((12 - 8) + 1); // с августа по декабрь
            month2 = 3 + random.nextInt((7 - 3) + 1);// с марта по июль
            year = 1970 + random.nextInt((2022 - 1970) + 1); //год с 1970 по 2022

            //условия для создания корректных строк совпадающих с шаблоном (**.**.****)
            if (day2 < 10 && month >= 10) {
                dateStart.append(day).append(".").append(month).append(".").append(year);
                dateEnd.append("0").append(day2).append(".").append("0").append(month2).append(".").append(year);
            } else if (month < 10 && day2 < 10) {
                dateStart.append(day).append(".").append("0").append(month).append(".").append(year);
                dateEnd.append("0").append(day2).append(".").append("0").append(month2).append(".").append(year);
            } else if (day2 >= 10 && month < 10) {
                dateStart.append(day).append(".").append("0").append(month).append(".").append(year);
                dateEnd.append(day2).append(".").append("0").append(month2).append(".").append(year);
            } else {
                dateStart.append(day).append(".").append(month).append(".").append(year);
                dateEnd.append(day2).append(".").append("0").append(month2).append(".").append(year);
            }

            assertFalse(checkDateValues.isBeforeDateEnd(String.valueOf(dateStart), String.valueOf(dateEnd)));
            dateStart.replace(0, 10, "");
            dateEnd.replace(0, 10, "");
        }
    }

    // Проверка переданных данных в метод isEqualsDates()
    @Test
    public void datesShouldBeEquals() {
        String dateStart = "01.03.2022";
        String dateEnd = "01.03.2022";

        assertTrue(checkDateValues.isEqualsDates(dateStart, dateEnd));
    }

    @Test
    public void datesShouldBeNotEquals() {
        String dateStart = "11.06.2022";
        String dateEnd = "12.07.2022";

        assertFalse(checkDateValues.isEqualsDates(dateStart, dateEnd));
    }
}