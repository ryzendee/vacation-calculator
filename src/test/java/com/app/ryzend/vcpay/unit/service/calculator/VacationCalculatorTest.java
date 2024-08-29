package com.app.ryzend.vcpay.unit.service.calculator;

import com.ryzend.vcpay.service.calculator.VacationCalculator;
import com.ryzend.vcpay.service.calculator.VacationCalculatorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class VacationCalculatorTest {

    private VacationCalculator vacationCalculator;

    @BeforeEach
    void setUp() {
        this.vacationCalculator = new VacationCalculatorImpl();
    }

    @Test
    void calculate_resultShouldBeEqualToExpected() {
        BigDecimal expected = new BigDecimal(20);

        BigDecimal avgSalary = new BigDecimal(2);
        int vacationDays = 10;

        BigDecimal result = vacationCalculator.calculate(avgSalary, vacationDays);
        assertThat(result).isEqualTo(expected);
    }
}
