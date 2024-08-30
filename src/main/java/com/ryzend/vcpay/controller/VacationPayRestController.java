package com.ryzend.vcpay.controller;

import com.ryzend.vcpay.dto.ExceptionResponse;
import com.ryzend.vcpay.dto.VacationPayResponse;
import com.ryzend.vcpay.service.VacationPayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> calculateVacPay(@RequestParam @Min(1) BigDecimal avgSalary,
                                               @RequestParam @Min(1) int vacationDays,
                                               @RequestParam(required = false) LocalDate from,
                                               @RequestParam(required = false) LocalDate to) {

        if (from != null && to != null && !from.isBefore(to)) {
            return ResponseEntity.badRequest()
                    .body(new ExceptionResponse("from-date must be before to-date"));
        }

        VacationPayResponse response = vacationPayService.calculateVacPay(avgSalary, vacationDays, from, to);
        return ResponseEntity.ok(response);
    }

}
