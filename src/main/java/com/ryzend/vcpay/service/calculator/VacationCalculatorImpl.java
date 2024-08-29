package com.ryzend.vcpay.service.calculator;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class VacationCalculatorImpl implements VacationCalculator {
    @Override
    public BigDecimal calculate(BigDecimal avgSalary, int vacationDays) {
        return avgSalary.multiply(new BigDecimal(vacationDays));
    }
}
