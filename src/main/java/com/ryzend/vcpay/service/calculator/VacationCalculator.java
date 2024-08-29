package com.ryzend.vcpay.service.calculator;

import java.math.BigDecimal;

public interface VacationCalculator {

    BigDecimal calculate(BigDecimal avgSalary, int vacationDays);
}
