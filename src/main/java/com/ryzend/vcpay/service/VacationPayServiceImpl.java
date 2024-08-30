package com.ryzend.vcpay.service;

import com.ryzend.vcpay.dto.VacationPayResponse;
import com.ryzend.vcpay.service.calculator.VacationCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class VacationPayServiceImpl implements VacationPayService {
    private static final int SCALE = 2;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

    private final VacationCalculator vacationCalculator;

    @Override
    public VacationPayResponse calculateVacPay(BigDecimal avgSalary, int vacationDays, LocalDate from, LocalDate to) {
        if (from != null && to != null) {
            vacationDays = countWeekdaysBetweenDates(from, to);
        }

        BigDecimal vacPay = vacationCalculator.calculate(avgSalary, vacationDays)
                .setScale(SCALE, ROUNDING_MODE);

        return new VacationPayResponse(vacPay);
    }

    private int countWeekdaysBetweenDates(LocalDate from, LocalDate to) {
        int count = 0;

        while (!from.isEqual(to)) {
            if (isWeekday(from.getDayOfWeek())) {
                count++;
            }

            from = from.plusDays(1);
        }

        return count;
    }

    private boolean isWeekday(DayOfWeek day) {
        return day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY;
    }
}
