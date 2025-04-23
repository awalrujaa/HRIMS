package com.HRIMS.hrims_backend.controller;

import com.HRIMS.hrims_backend.dto.DepartmentRequest;
import com.HRIMS.hrims_backend.dto.DepartmentResponse;
import com.HRIMS.hrims_backend.dto.PaginatedResponse;
import com.HRIMS.hrims_backend.dto.ApiResponse;
import com.HRIMS.hrims_backend.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    public ApiResponse<DepartmentResponse> createDepartment(@Valid @RequestBody DepartmentRequest departmentRequest, Principal principal){
        log.info("current user: {}", principal.getName());
        return departmentService.createDepartment(departmentRequest);
    }

    @GetMapping
    public ApiResponse<PaginatedResponse<DepartmentResponse>> getDepartments(@RequestParam(defaultValue = "0") int pageNum,
                                                                             @RequestParam(defaultValue = "10") int pageSize){
            return departmentService.getAllDepartments(pageNum, pageSize);
    }

    @GetMapping("/{departmentId}")
    public ApiResponse<DepartmentResponse> getDepartmentById(@PathVariable Long departmentId){
        return departmentService.getDepartmentById(departmentId);
    }

    @PutMapping("/{departmentId}")
    public ApiResponse<DepartmentResponse> updateDepartment(@PathVariable Long departmentId,
                                                            @RequestBody @Valid DepartmentRequest departmentRequest){
        return departmentService.updateDepartment(departmentId, departmentRequest);
    }

    @DeleteMapping("/{departmentId}")
    public ApiResponse<String> deleteDepartmentById(@PathVariable Long departmentId){
        return departmentService.deleteDepartmentById(departmentId);
    }

}
