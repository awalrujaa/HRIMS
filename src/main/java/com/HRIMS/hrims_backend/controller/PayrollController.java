package com.HRIMS.hrims_backend.controller;

import com.HRIMS.hrims_backend.dto.PayrollDto;
import com.HRIMS.hrims_backend.service.PayrollService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payroll")
public class PayrollController {

    private final PayrollService payrollService;

    @PostMapping
    PayrollDto createPayroll(@RequestBody PayrollDto payrollDto){
        return payrollService.createPayroll(payrollDto);
    }

}
