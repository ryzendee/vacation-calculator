package com.ryzend.vcpay.service;

import com.ryzend.vcpay.dto.VacationPayResponse;
import com.ryzend.vcpay.service.calculator.VacationCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class VacationPayServiceImpl implements VacationPayService {

    private final VacationCalculator vacationCalculator;

    @Override
    public VacationPayResponse calculateVacPay(BigDecimal avgSalary, int vacationDays) {
        BigDecimal vacPay = vacationCalculator.calculate(avgSalary, vacationDays);

        return new VacationPayResponse(vacPay);
    }
}
