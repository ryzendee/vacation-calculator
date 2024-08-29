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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VacationPayServiceTest {

    @InjectMocks
    private VacationPayServiceImpl vacationPayService;

    @Mock
    private VacationCalculator vacationCalculator;

    @Test
    void calculateVacPay_shouldInvokeCalculatorAndReturnExpectedResponse() {
        BigDecimal calculationResult = new BigDecimal(20);
        VacationPayResponse expectedResponse = new VacationPayResponse(calculationResult);

        BigDecimal avgSalary = new BigDecimal(2);
        int vacationDays = 10;
        when(vacationCalculator.calculate(avgSalary, vacationDays)).thenReturn(calculationResult);

        VacationPayResponse actualResponse = vacationPayService.calculateVacPay(avgSalary, vacationDays);
        assertThat(actualResponse).isEqualTo(expectedResponse);
    }
}
