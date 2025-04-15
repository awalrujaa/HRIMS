package com.HRIMS.hrims_backend.service.impl;

import com.HRIMS.hrims_backend.dto.PayrollDto;
import com.HRIMS.hrims_backend.entity.Employee;
import com.HRIMS.hrims_backend.entity.Leave;
import com.HRIMS.hrims_backend.entity.Payroll;
import com.HRIMS.hrims_backend.enums.LeaveStatus;
import com.HRIMS.hrims_backend.mapper.PayrollMapper;
import com.HRIMS.hrims_backend.repository.EmployeeRepository;
import com.HRIMS.hrims_backend.repository.PayrollRepository;
import com.HRIMS.hrims_backend.service.PayrollService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PayrollServiceImpl implements PayrollService {

    private final PayrollRepository payrollRepository;
    private final EmployeeRepository employeeRepository;
    private final PayrollMapper payrollMapper;


    @Override
    public PayrollDto createPayroll(PayrollDto payrollDto) {
        Long empId = payrollDto.getEmployeeId();
        Optional<Employee> employee = employeeRepository.findById(empId);
        if(employee.isEmpty()){
            throw new RuntimeException("Employee doesn't exist with id: " + empId);
        }
        Payroll payroll = payrollMapper.toPayrollEntity(payrollDto);
        payrollRepository.save(payroll);
        return payrollMapper.toPayrollDto(payroll);
    }

    @Override
    public List<PayrollDto> getAllPayrolls() {
        return List.of();
    }

    @Override
    public PayrollDto getPayrollById(Long payrollId) {
        return null;
    }

    @Override
    public PayrollDto updatePayroll(PayrollDto payrollDetails) {
        return null;
    }

    @Override
    public String deletePayroll(Long payrollId) {
        return "";
    }
}
