package com.HRIMS.hrims_backend.controller;

import com.HRIMS.hrims_backend.dto.*;
import com.HRIMS.hrims_backend.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ApiResponse<EmployeeResponse> createEmployee(@RequestBody @Valid EmployeeRequest employeeRequest){
        return employeeService.createEmployee(employeeRequest);
    }

    @GetMapping
    public ApiResponse<PaginatedResponse<EmployeeResponse>> getAllEmployees(@RequestParam(defaultValue = "0") int pageNum,
                                                                            @RequestParam(defaultValue = "10") int pageSize) {
        return employeeService.getAllEmployees(pageNum, pageSize);
    }

    @GetMapping("/{employeeId}")
    public ApiResponse<EmployeeResponse> getEmployeeById(@PathVariable Long employeeId){
        return employeeService.getEmployeeById(employeeId);
    }

    @PutMapping("/{employeeId}")
    public ApiResponse<EmployeeResponse> updateEmployee(@PathVariable Long employeeId, @RequestBody @Valid EmployeeRequest employeeDetails){
        return employeeService.updateEmployee(employeeId, employeeDetails);
    }

    @DeleteMapping("/{employeeId}")
    public ApiResponse<String> deleteEmployee(@PathVariable Long employeeId){
        return employeeService.deleteEmployee(employeeId);
    }

    @GetMapping("/filter")
    public ApiResponse<PaginatedResponse<EmployeeResponse>> filterEmployees(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String middleName,
//            @RequestParam(required = false) String lastName,
            @RequestParam(defaultValue = "0") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        return employeeService.filterEmployees(email, middleName, pageNum, pageSize);
    }
}
