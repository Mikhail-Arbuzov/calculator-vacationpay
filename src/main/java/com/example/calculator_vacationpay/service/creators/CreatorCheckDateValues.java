package com.example.calculator_vacationpay.service.creators;

import com.example.calculator_vacationpay.service.check.CheckingDateValues;
import com.example.calculator_vacationpay.service.check.ICheckDateValues;

//Класс для создания и инициализации объекта реализующего интерфейс ICheckDateValues
public class CreatorCheckDateValues implements ICreatorCheckDateValues {
    @Override
    public ICheckDateValues createCheckDateValues() {
        return CheckingDateValues.getInstance();
    }
}
