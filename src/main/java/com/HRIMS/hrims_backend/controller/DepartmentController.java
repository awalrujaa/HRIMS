package com.HRIMS.hrims_backend.controller;

import com.HRIMS.hrims_backend.dto.DepartmentDto;
import com.HRIMS.hrims_backend.entity.Department;
import com.HRIMS.hrims_backend.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService){
        this.departmentService = departmentService;
    }

    @PostMapping
    public DepartmentDto createDepartment(@RequestBody DepartmentDto departmentDto){
        return departmentService.createDepartment(departmentDto);
    }

    @GetMapping
    public List<DepartmentDto> getDepartments(){
        return departmentService.getAllDepartments();
    }

    @GetMapping("/{departmentId}")
    public DepartmentDto getDepartmentById(@PathVariable Long departmentId){
        return departmentService.getDepartmentById(departmentId);
    }

    @PutMapping("/{departmentId}")
    public DepartmentDto updateDepartment(@PathVariable Long departmentId, @RequestBody DepartmentDto departmentDto){
        return departmentService.updateDepartment(departmentId, departmentDto);
    }

    @DeleteMapping("/{departmentId}")
    public String deleteDepartmentById(@PathVariable Long departmentId){
        return departmentService.deleteDepartmentById(departmentId);
    }

    @DeleteMapping
    public String deleteDepartments(){
        return departmentService.deleteDepartments();
    }
}
