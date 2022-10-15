package com.example.calculator_vacationpay.service.calculation;

import com.example.calculator_vacationpay.service.creators.CreatorCalculateNumberOfVacationDays;
import com.example.calculator_vacationpay.service.creators.ICreatorCalculateNumberOfVacationDays;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorVacationDatesTest {
    private static ICreatorCalculateNumberOfVacationDays creatorCalculateNumberOfVacationDays
            = new CreatorCalculateNumberOfVacationDays();

    private static ICalculateNumberOfVacationDays calculateNumberOfVacationDays;

    private static Random random;

    @BeforeAll
    public static void initialCalculatorVacationDates(){
        calculateNumberOfVacationDays = creatorCalculateNumberOfVacationDays.createCalculateNumberOfVacationDays();
        random = new Random();
    }

    @Test
    public void methodShouldReturnNumberOfVacationDays(){
        int day = 0;
        int month = 0;
        int year = 0;

        for (int i = 0; i <= 1000; i++){
            //дата в диапазоне от 1 до 30, с учетом того, что во всех месяцах кроме февраля может быть 30 дней
            day = 1 + random.nextInt((30 - 1) + 1);
            month = 1 + random.nextInt((12 - 1) + 1);
            //год в диапазоне от 1970 до 2022
            year = 1970 + random.nextInt((2022 - 1970) + 1);
            //если феврале окажется 30 дней, то пропускаем итерацию
            if(month == 2 && day == 30){
                continue;
            }
            //если в феврале окажется 29 дней в невисокосный год, то пропускаем итерацию
            if (month == 2 && day == 29 && year % 4 != 0){
                continue;
            }
            LocalDate dateStart = LocalDate.of(year,month,day);
            //Конечная дата определяется с учетом того, что количество дней отпуска должно составить 14 дней
            // и дата окончания отпуска должна быть четырнадцатой по счету
            LocalDate dateEnd = dateStart.plusDays(14).minusDays(1);
            //Если отпуск был взят на 14 дней, то метод getNumberOfVacationDays() должен вернуть 14
            assertEquals(14, calculateNumberOfVacationDays.getNumberOfVacationDays(dateStart,dateEnd),
                    "Метод getNumberOfVacationDays() должен вернуть 14");
        }
    }

    //Тест для проверки метода getNumberOfVacationDays(), который должен вернуть
    // количество дней отпуска не считая праздничных дней

    @Test
    public void methodShouldReturnNumberOfVacationDaysNotCountHolidays(){
        //Отпуск взят с 26.12.22 по 22.01.2023, что должно составить 28 дней
        // и в данный период отпуска, не должны включаться январские праздничные дни: 1,2,3,4,5,6,7,8
        LocalDate startDate = LocalDate.of(2022,12,26);
        LocalDate endDate = LocalDate.of(2023,1,22);
        //Если отпуск был взят на 28 дней, то метод getNumberOfVacationDays() должен вернуть 28
        assertEquals(28,calculateNumberOfVacationDays.getNumberOfVacationDays(startDate,endDate));
    }
}