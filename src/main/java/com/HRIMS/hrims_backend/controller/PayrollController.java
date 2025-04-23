package com.HRIMS.hrims_backend.controller;

import com.HRIMS.hrims_backend.dto.ApiResponse;
import com.HRIMS.hrims_backend.dto.PaginatedResponse;
import com.HRIMS.hrims_backend.dto.PayrollDto;
import com.HRIMS.hrims_backend.service.PayrollService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payroll")
public class PayrollController {

    private final PayrollService payrollService;

    @PostMapping
    public ApiResponse<PayrollDto> createPayroll(@RequestBody PayrollDto payrollDto){
        return payrollService.createPayroll(payrollDto);
    }

    @GetMapping
    public ApiResponse<PaginatedResponse<PayrollDto>> getAllPayrolls(@RequestParam(defaultValue = "0") int pageNum,
                                                                     @RequestParam(defaultValue = "10") int pageSize){
        return payrollService.getAllPayrolls(pageNum, pageSize);
    }

    @GetMapping("/{payrollId}")
    public ApiResponse<PayrollDto> getPayrollById(@PathVariable Long payrollId){
        return payrollService.getPayrollById(payrollId);
    }

    @PutMapping("/{payrollId}")
    public ApiResponse<PayrollDto> updatePayroll(@PathVariable Long payrollId, @RequestBody PayrollDto payrollDetails) {
        return payrollService.updatePayroll(payrollId, payrollDetails);
    }

    @DeleteMapping("/{payrollId}")
    ApiResponse<String> deletePayroll(@PathVariable Long payrollId){
        return payrollService.deletePayroll(payrollId);
    }

}
