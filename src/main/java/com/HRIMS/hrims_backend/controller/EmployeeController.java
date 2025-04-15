package com.HRIMS.hrims_backend.controller;

import com.HRIMS.hrims_backend.dto.EmployeeDto;
import com.HRIMS.hrims_backend.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public EmployeeDto createEmployee(@RequestBody EmployeeDto employeeDto){
        return employeeService.createEmployee(employeeDto);
    }

    @GetMapping
    public List<EmployeeDto> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{employeeId}")
    public EmployeeDto getEmployeeById(@PathVariable Long employeeId){
        return employeeService.getEmployeeById(employeeId);
    }

    @PutMapping("/{employeeId}")
    public EmployeeDto updateEmployee(@PathVariable Long employeeId, @RequestBody EmployeeDto employeeDetailsDto){
        return employeeService.updateEmployee(employeeId, employeeDetailsDto);
    }

    @DeleteMapping("/{employeeId}")
    public String deleteEmployee(@PathVariable Long employeeId){
        return employeeService.deleteEmployee(employeeId);
    }
}
