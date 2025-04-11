package com.ryzend.vcpay.controller;

import com.ryzend.vcpay.dto.VacationPayResponse;
import com.ryzend.vcpay.service.VacationPayService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDate;


@RestController
@RequestMapping("/calculate")
@Validated
@RequiredArgsConstructor
public class VacationPayRestController {

    private final VacationPayService vacationPayService;

    @GetMapping
    public VacationPayResponse calculateVacPay(@RequestParam @Min(1) BigDecimal avgSalary,
                                               @RequestParam @Min(1) int vacationDays,
                                               @RequestParam(required = false) LocalDate from,
                                               @RequestParam(required = false) LocalDate to) {

        if (isFromAfterTo(from, to)) {
            throw new IllegalArgumentException("From date must be before to date");
        }

        return vacationPayService.calculateVacPay(avgSalary, vacationDays, from, to);
    }

    private boolean isFromAfterTo(LocalDate from, LocalDate to) {
        return from != null && to != null && from.isAfter(to);
    }
}
