package com.HRIMS.hrims_backend.controller;

import com.HRIMS.hrims_backend.dto.PayrollDto;
import com.HRIMS.hrims_backend.service.PayrollService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payroll")
public class PayrollController {

    private final PayrollService payrollService;

    @PostMapping
    PayrollDto createPayroll(@RequestBody PayrollDto payrollDto){
        return payrollService.createPayroll(payrollDto);
    }

    @GetMapping
    List<PayrollDto> getAllPayrolls(){
        return payrollService.getAllPayrolls();
    }

    @GetMapping("/{payrollId}")
    PayrollDto getPayrollById(@PathVariable Long payrollId){
        return payrollService.getPayrollById(payrollId);
    }

    @PutMapping("/{payrollId}")
    PayrollDto updatePayroll(@PathVariable Long payrollId, @RequestBody PayrollDto payrollDetails) {
        return payrollService.updatePayroll(payrollId, payrollDetails);
    }

    @DeleteMapping("/{payrollId}")
    String deletePayroll(@PathVariable Long payrollId){
        return payrollService.deletePayroll(payrollId);
    }

}
