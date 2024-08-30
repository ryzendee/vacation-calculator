package com.app.ryzend.vcpay.unit.controller;

import com.ryzend.vcpay.VacationPayCalculatorApplication;
import com.ryzend.vcpay.controller.VacationPayRestController;
import com.ryzend.vcpay.dto.VacationPayResponse;
import com.ryzend.vcpay.service.VacationPayService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Named.named;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(VacationPayRestController.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = VacationPayCalculatorApplication.class)
public class VacationPayRestControllerTest {

    private static final String BASE_URI = "/calculate";
    private static final String AVG_SALARY_PARAM = "avgSalary";
    private static final String VACATION_DAYS_PARAM = "vacationDays";
    private static final String FROM_PARAM = "from";
    private static final String TO_PARAM = "to";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VacationPayService vacationPayService;


    @DisplayName("Calculate vac pay: without dates, should return response")
    @Test
    void calculateVacPay_withoutDateParams_shouldReturnResponse() throws Exception {
        VacationPayResponse response = new VacationPayResponse(new BigDecimal(50));
        when(vacationPayService.calculateVacPay(any(BigDecimal.class), anyInt(), any(), any()))
                .thenReturn(response);

        mockMvc.perform(
                get(BASE_URI)
                        .queryParam(AVG_SALARY_PARAM, "10")
                        .queryParam(VACATION_DAYS_PARAM, "5")
        ).andExpectAll(
                status().isOk()
        );

        verify(vacationPayService).calculateVacPay(any(BigDecimal.class), anyInt(), any(), any());
    }

    @DisplayName("Calculate vac pay: with dates, should return response")
    @Test
    void calculateVacPay_withDateParams_shouldReturnResponse() throws Exception {
        VacationPayResponse response = new VacationPayResponse(new BigDecimal(50));

        when(vacationPayService.calculateVacPay(any(BigDecimal.class), anyInt(), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(response);

        LocalDate fromDate = LocalDate.now();
        LocalDate toDate = LocalDate.now().plusDays(5);
        mockMvc.perform(
                get(BASE_URI)
                        .queryParam(AVG_SALARY_PARAM, "10")
                        .queryParam(VACATION_DAYS_PARAM, "5")
                        .queryParam(FROM_PARAM, fromDate.toString())
                        .queryParam(TO_PARAM, toDate.toString())
        ).andExpectAll(
                status().isOk()
        );

        verify(vacationPayService).calculateVacPay(any(BigDecimal.class), anyInt(), any(LocalDate.class), any(LocalDate.class));
    }

    @DisplayName("Calculate vac pay: should return status bad request because parameters are invalid")
    @MethodSource("getInvalidRequestParams")
    @ParameterizedTest
    void calculateVacPay_invalidRequestParams_shouldReturnStatusBadRequest(MultiValueMap<String, String> paramMap) throws Exception {
        mockMvc.perform(
                get(BASE_URI)
                        .queryParams(paramMap)
        ).andExpectAll(
                status().isBadRequest()
        );

        verify(vacationPayService, never()).calculateVacPay(any(BigDecimal.class), anyInt(), any(), any());
    }

    static Stream<Arguments> getInvalidRequestParams() {
        MultiValueMap<String, String> invalidSalaryParamMap = new LinkedMultiValueMap<>();
        invalidSalaryParamMap.put(AVG_SALARY_PARAM, List.of("0"));
        invalidSalaryParamMap.put(VACATION_DAYS_PARAM, List.of("1"));

        MultiValueMap<String, String> invalidVacationDaysParamMap = new LinkedMultiValueMap<>();
        invalidVacationDaysParamMap.put(AVG_SALARY_PARAM, List.of("1"));
        invalidVacationDaysParamMap.put(VACATION_DAYS_PARAM, List.of("0"));

        MultiValueMap<String, String> futureFromDateMap = new LinkedMultiValueMap<>();
        LocalDate fromDate = LocalDate.now().plusDays(5);
        futureFromDateMap.put(FROM_PARAM, List.of(fromDate.toString()));

        MultiValueMap<String, String> pastToDateMap = new LinkedMultiValueMap<>();
        LocalDate pastToDate = LocalDate.now().minusDays(5);
        pastToDateMap.put(TO_PARAM, List.of(pastToDate.toString()));

        MultiValueMap<String, String> presentToDateMap = new LinkedMultiValueMap<>();
        LocalDate presentToDate = LocalDate.now();
        presentToDateMap.put(TO_PARAM, List.of(presentToDate.toString()));


        return Stream.of(
                arguments(named("Salary less than one", invalidSalaryParamMap)),
                arguments(named("Num of vacation days less that one", invalidVacationDaysParamMap)),
                arguments(named("From date is future", futureFromDateMap)),
                arguments(named("To date is past", pastToDateMap)),
                arguments(named("To date is present", presentToDateMap))
        );
    }
}
