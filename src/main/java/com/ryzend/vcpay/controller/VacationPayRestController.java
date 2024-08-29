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


@RestController
@RequestMapping("/calculate")
@Validated
@RequiredArgsConstructor
public class VacationPayRestController {

    private final VacationPayService vacationPayService;

    @GetMapping
    public VacationPayResponse calculateVacPay(@RequestParam @Min(1) BigDecimal avgSalary,
                                               @RequestParam @Min(1) int vacationDays) {
        return vacationPayService.calculateVacPay(avgSalary, vacationDays);
    }

}
