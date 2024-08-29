package com.ryzend.vcpay.service;

import com.ryzend.vcpay.dto.VacationPayResponse;

import java.math.BigDecimal;

public interface VacationPayService {

    VacationPayResponse calculateVacPay(BigDecimal avgSalary, int vacationDays);

}
