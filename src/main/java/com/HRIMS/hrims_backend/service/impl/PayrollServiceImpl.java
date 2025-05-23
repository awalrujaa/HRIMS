package com.HRIMS.hrims_backend.service.impl;

import com.HRIMS.hrims_backend.dto.ApiResponse;
import com.HRIMS.hrims_backend.dto.PaginatedResponse;
import com.HRIMS.hrims_backend.dto.PayrollDto;
import com.HRIMS.hrims_backend.entity.Employee;
import com.HRIMS.hrims_backend.entity.Payroll;
import com.HRIMS.hrims_backend.exception.ResourceNotFoundException;
import com.HRIMS.hrims_backend.mapper.PayrollMapper;
import com.HRIMS.hrims_backend.repository.EmployeeRepository;
import com.HRIMS.hrims_backend.repository.PayrollRepository;
import com.HRIMS.hrims_backend.service.PayrollService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PayrollServiceImpl implements PayrollService {

    private final PayrollRepository payrollRepository;
    private final EmployeeRepository employeeRepository;
    private final PayrollMapper payrollMapper;


    @Override
    public ApiResponse<PayrollDto> createPayroll(PayrollDto payrollDto) {
        Long empId = payrollDto.getEmployeeId();
        employeeRepository.findById(empId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee doesn't exist with id: " + empId));

        Payroll payroll = payrollMapper.toPayrollEntity(payrollDto);
        double deduction = payroll.getCit() + payroll.getPf() + payroll.getSsf() + payroll.getTax() + payroll.getOthers();
        double netSalary = payroll.getBasicSalary() + payroll.getDashainAllowance() + payroll.getBonus() + payroll.getPerformanceBonus() - deduction;
        payroll.setNetSalary(netSalary);
        payrollRepository.save(payroll);
        return new ApiResponse<>(HttpStatus.OK.value(),
                "Created Payroll Successfully.", HttpStatus.OK.name(), LocalDateTime.now(),
                payrollMapper.toPayrollDto(payroll), new ArrayList<>());
    }

    @Override
    public ApiResponse<PaginatedResponse<PayrollDto>> getAllPayrolls(int pageNum, int pageSize) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<Payroll> payrolls = payrollRepository.findAll(pageable);
        List<PayrollDto> payrollResponses = payrolls
                .stream()
                .map(payrollMapper::toPayrollDto)
                .collect(Collectors.toList());

        PaginatedResponse<PayrollDto> paginatedData = new PaginatedResponse<>(
                payrollResponses,
                payrolls.getTotalPages(),
                payrolls.getTotalElements(),
                payrolls.getNumber(),
                payrolls.getSize()
        );
        return new ApiResponse<>(HttpStatus.OK.value(),
                "Retrieved Payrolls Successfully.", HttpStatus.OK.name(), LocalDateTime.now(),
                paginatedData, new ArrayList<>());
    }

    @Override
    public ApiResponse<PayrollDto> getPayrollById(Long id) {
        Payroll payroll = payrollRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payroll not found with id " + id));
        return new ApiResponse<>(HttpStatus.OK.value(),
                "Payroll retrieved successfully.", HttpStatus.OK.name(), LocalDateTime.now(),
                payrollMapper.toPayrollDto(payroll), new ArrayList<>());
         }

    @Override
    public ApiResponse<PayrollDto> updatePayroll(Long id, PayrollDto payrollDetails) {

        Payroll payroll = payrollRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payroll not found with id " + id));
        long empId = payroll.getEmployee().getId();
        payroll.setEmployee(Employee.builder().id(empId).build());
        payroll.setBasicSalary(payrollDetails.getBasicSalary());
        payroll.setDashainAllowance(payrollDetails.getDashainAllowance());
        payroll.setBonus(payrollDetails.getBonus());
        payroll.setPerformanceBonus(payrollDetails.getPerformanceBonus());
        payroll.setCit(payrollDetails.getCit());
        payroll.setPf(payrollDetails.getPf());
        payroll.setSsf(payrollDetails.getSsf());
        payroll.setTax(payrollDetails.getTax());
        payroll.setOthers(payrollDetails.getOthers());
        payroll.setPayDate(payrollDetails.getPayDate());
        double deduction = payrollDetails.getCit() + payrollDetails.getPf() + payrollDetails.getSsf() + payrollDetails.getTax() + payrollDetails.getOthers();
        double netSalary = payrollDetails.getBasicSalary() + payrollDetails.getDashainAllowance() + payrollDetails.getBonus() + payrollDetails.getPerformanceBonus() - deduction;
        payroll.setNetSalary(netSalary);
        payrollRepository.save(payroll);
        return new ApiResponse<>(HttpStatus.OK.value(),
                "Payroll updated successfully.", HttpStatus.OK.name(), LocalDateTime.now(),
                payrollMapper.toPayrollDto(payroll), new ArrayList<>());
    }

    @Override
    public ApiResponse<String> deletePayroll(Long id) {
        payrollRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payroll not found with id " + id));

        payrollRepository.deleteById(id);
        return new ApiResponse<>(HttpStatus.NO_CONTENT.value(),
                "Payroll Deleted Successfully.", HttpStatus.NO_CONTENT.name(), LocalDateTime.now(),
                "Payroll with ID " + id + " deleted successfully.", new ArrayList<>());
    }
}
