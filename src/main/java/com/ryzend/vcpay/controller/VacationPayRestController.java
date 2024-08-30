package com.ryzend.vcpay.controller;

import com.ryzend.vcpay.dto.VacationPayResponse;
import com.ryzend.vcpay.service.VacationPayService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.PastOrPresent;
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
                                               @RequestParam(required = false) @PastOrPresent LocalDate from,
                                               @RequestParam(required = false) @Future LocalDate to) {
        return vacationPayService.calculateVacPay(avgSalary, vacationDays, from, to);
    }

}
