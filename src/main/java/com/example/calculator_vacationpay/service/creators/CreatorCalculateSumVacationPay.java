package com.example.calculator_vacationpay.service.creators;

import com.example.calculator_vacationpay.service.calculation.CalculatorSumVacationPay;
import com.example.calculator_vacationpay.service.calculation.ICalculateSumVacationPay;

//Класс для создания и инициализации объектов реализующих интерфейс ICalculateSumVacationPay
public class CreatorCalculateSumVacationPay implements ICreatorCalculateSumVacationPay {
    @Override
    public ICalculateSumVacationPay createCalculateSumVacationPay() {
        return CalculatorSumVacationPay.getInstance();
    }
}
