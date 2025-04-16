package com.HRIMS.hrims_backend.service;

import com.HRIMS.hrims_backend.dto.PayrollDto;

import java.util.List;

public interface PayrollService {

    PayrollDto createPayroll(PayrollDto payrollDto);

    List<PayrollDto> getAllPayrolls();

    PayrollDto getPayrollById(Long payrollId);

    PayrollDto updatePayroll(Long payrollId, PayrollDto payrollDetails);

    String deletePayroll(Long payrollId);
}
