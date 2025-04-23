package com.HRIMS.hrims_backend.service;

import com.HRIMS.hrims_backend.dto.ApiResponse;
import com.HRIMS.hrims_backend.dto.DepartmentResponse;
import com.HRIMS.hrims_backend.dto.PaginatedResponse;
import com.HRIMS.hrims_backend.dto.PayrollDto;

import java.util.List;

public interface PayrollService {

    ApiResponse<PayrollDto> createPayroll(PayrollDto payrollDto);

    ApiResponse<PaginatedResponse<PayrollDto>> getAllPayrolls(int pageNum, int pageSize);

    ApiResponse<PayrollDto> getPayrollById(Long payrollId);

    ApiResponse<PayrollDto> updatePayroll(Long payrollId, PayrollDto payrollDetails);

    ApiResponse<String> deletePayroll(Long payrollId);
}
