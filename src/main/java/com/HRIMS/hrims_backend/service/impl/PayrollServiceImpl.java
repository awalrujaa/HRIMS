package com.HRIMS.hrims_backend.service.impl;

import com.HRIMS.hrims_backend.dto.PayrollDto;
import com.HRIMS.hrims_backend.entity.Attendance;
import com.HRIMS.hrims_backend.entity.Employee;
import com.HRIMS.hrims_backend.entity.Leave;
import com.HRIMS.hrims_backend.entity.Payroll;
import com.HRIMS.hrims_backend.mapper.PayrollMapper;
import com.HRIMS.hrims_backend.repository.EmployeeRepository;
import com.HRIMS.hrims_backend.repository.PayrollRepository;
import com.HRIMS.hrims_backend.service.PayrollService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        double deduction = payroll.getCit() + payroll.getPf() + payroll.getSsf() + payroll.getTax() + payroll.getOthers();
        double netSalary = payroll.getBasicSalary() + payroll.getDashainAllowance() + payroll.getBonus() + payroll.getPerformanceBonus() - deduction;
        payroll.setNetSalary(netSalary);
        payrollRepository.save(payroll);
        return payrollMapper.toPayrollDto(payroll);
    }

    @Override
    public List<PayrollDto> getAllPayrolls() {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        List<Payroll> payrollList = payrollRepository.findAll();
        return payrollList.stream().map(payrollMapper::toPayrollDto).collect(Collectors.toList());
    }

    @Override
    public PayrollDto getPayrollById(Long payrollId) {
        Optional<Payroll> optionalPayroll = payrollRepository.findById(payrollId);
        if(optionalPayroll.isEmpty()){
            throw new RuntimeException("No such payroll data.");
        }
        Payroll payroll = optionalPayroll.get();
        return payrollMapper.toPayrollDto(payroll);
         }

    @Override
    public PayrollDto updatePayroll(Long payrollId, PayrollDto payrollDetails) {

        Optional<Payroll> optionalPayroll = payrollRepository.findById(payrollId);
        if(optionalPayroll.isEmpty()){
            throw new RuntimeException("Payroll data not found with id " + payrollId);
        }
        Payroll payroll = optionalPayroll.get();
        long empId = payroll.getEmployee().getId();
        payroll.setEmployee(Employee.builder().id(empId).build());
        payroll.setBasicSalary(payrollDetails.getBasicSalary());
        System.out.println(payroll.getBasicSalary());
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
        return payrollMapper.toPayrollDto(payroll);
    }

    @Override
    public String deletePayroll(Long payrollId) {
        Optional<Payroll> optionalPayroll = payrollRepository.findById(payrollId);
        if(optionalPayroll.isEmpty()){
            return "Payroll Data not found with id " + payrollId;
        }
        payrollRepository.delete(optionalPayroll.get());
        return "Success";
    }
}
