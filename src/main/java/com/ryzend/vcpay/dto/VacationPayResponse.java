package com.ryzend.vcpay.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class VacationPayResponse {
    private final BigDecimal vacationPay;
}
