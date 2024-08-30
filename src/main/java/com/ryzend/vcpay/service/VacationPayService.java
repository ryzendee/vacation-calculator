package com.ryzend.vcpay.service;

import com.ryzend.vcpay.dto.VacationPayResponse;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface VacationPayService {
    VacationPayResponse calculateVacPay(BigDecimal avgSalary, int vacationDays)
    VacationPayResponse calculateVacPay(BigDecimal avgSalary, int vacationDays, LocalDate from, LocalDate to);
}
