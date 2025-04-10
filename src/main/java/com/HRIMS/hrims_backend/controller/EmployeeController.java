package com.HRIMS.hrims_backend.controller;

import com.HRIMS.hrims_backend.dto.request.EmployeeRequestDto;
import com.HRIMS.hrims_backend.dto.response.EmployeeResponseDto;
import com.HRIMS.hrims_backend.entity.Employee;
import com.HRIMS.hrims_backend.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee){
        return employeeService.createEmployee(employee);
    }

    @GetMapping
    public List<EmployeeResponseDto> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{employeeId}")
    public Optional<Employee> getEmployeeById(@PathVariable Long employeeId){
        return employeeService.getEmployeeById(employeeId);
    }

    @PutMapping("/{employeeId}")
    public Employee updateEmployee(@PathVariable Long employeeId, @RequestBody Employee employeeDetails){
        return employeeService.updateEmployee(employeeId, employeeDetails);
    }

    @DeleteMapping("/{employeeId}")
    public String deleteEmployee(@PathVariable Long employeeId){
        return employeeService.deleteEmployee(employeeId);
    }
}
