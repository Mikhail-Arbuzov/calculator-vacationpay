package com.example.calculator_vacationpay.service.creators;

import com.example.calculator_vacationpay.service.calculation.CalculatorVacationDates;
import com.example.calculator_vacationpay.service.calculation.ICalculateNumberOfVacationDays;

//Класс для создания и инициализации объекта реализующего интерфейс ICalculateNumberOfVacationDays
public class CreatorCalculateNumberOfVacationDays implements ICreatorCalculateNumberOfVacationDays {
    @Override
    public ICalculateNumberOfVacationDays createCalculateNumberOfVacationDays() {
        return CalculatorVacationDates.getInstance();
    }
}
