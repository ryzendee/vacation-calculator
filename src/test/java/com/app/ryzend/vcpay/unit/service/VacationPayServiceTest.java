package com.app.ryzend.vcpay.unit.service;

import com.ryzend.vcpay.dto.VacationPayResponse;
import com.ryzend.vcpay.service.VacationPayServiceImpl;
import com.ryzend.vcpay.service.calculator.VacationCalculator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LOCAL_DATE;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VacationPayServiceTest {

    private static final int SCALE = 2;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

    @InjectMocks
    private VacationPayServiceImpl vacationPayService;

    @Mock
    private VacationCalculator vacationCalculator;

    @Test
    void calculateVacPay_withDates_shouldInvokeCalculatorAndReturnExpectedResponse() {
        //given
        BigDecimal avgSalary = new BigDecimal("1000.00");
        int vacationDays = 1;  //Will be recalculated because dates are not null
        LocalDate from = LocalDate.of(2024, 8, 26); // Monday
        LocalDate to = LocalDate.of(2024, 9, 2); // Next monday

        int expectedVacationDays = 5;

        BigDecimal calculatedPay = new BigDecimal("1500.00");
        when(vacationCalculator.calculate(avgSalary, expectedVacationDays)).thenReturn(calculatedPay);
        BigDecimal expectedResult = calculatedPay.setScale(SCALE, ROUNDING_MODE);


        //when
        VacationPayResponse actualResponse = vacationPayService.calculateVacPay(avgSalary, vacationDays, from, to);

        //then
        verify(vacationCalculator).calculate(avgSalary, expectedVacationDays);
        assertThat(actualResponse.getVacationPay()).isEqualTo(expectedResult);
    }

    @Test
    void calculateVacPay_withoutDates_shouldInvokeCalculatorAndReturnExpectedResponse() {
        //given
        BigDecimal avgSalary = new BigDecimal(2);
        int vacationDays = 10;

        BigDecimal calculatedPay = new BigDecimal(20);
        when(vacationCalculator.calculate(avgSalary, vacationDays)).thenReturn(calculatedPay);
        BigDecimal expectedResult = calculatedPay.setScale(SCALE, ROUNDING_MODE);

        //when
        VacationPayResponse actualResponse = vacationPayService.calculateVacPay(avgSalary, vacationDays, null, null);

        //then
        verify(vacationCalculator).calculate(avgSalary, vacationDays);
        assertThat(actualResponse.getVacationPay()).isEqualTo(expectedResult);
    }
}
