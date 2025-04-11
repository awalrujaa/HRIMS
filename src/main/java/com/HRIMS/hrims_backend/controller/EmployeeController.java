package com.HRIMS.hrims_backend.controller;

import com.HRIMS.hrims_backend.dto.request.EmployeeRequestDto;
import com.HRIMS.hrims_backend.dto.response.EmployeeResponseDto;
import com.HRIMS.hrims_backend.entity.Employee;
import com.HRIMS.hrims_backend.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public EmployeeRequestDto createEmployee(@RequestBody EmployeeRequestDto employeeRequestDto){
        return employeeService.createEmployee(employeeRequestDto);
    }

    @GetMapping
    public List<EmployeeRequestDto> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{employeeId}")
    public EmployeeRequestDto getEmployeeById(@PathVariable Long employeeId){
        return employeeService.getEmployeeById(employeeId);
    }

    @PutMapping("/{employeeId}")
    public EmployeeRequestDto updateEmployee(@PathVariable Long employeeId, @RequestBody EmployeeRequestDto employeeDetailsDto){
        return employeeService.updateEmployee(employeeId, employeeDetailsDto);
    }

    @DeleteMapping("/{employeeId}")
    public String deleteEmployee(@PathVariable Long employeeId){
        return employeeService.deleteEmployee(employeeId);
    }
}
