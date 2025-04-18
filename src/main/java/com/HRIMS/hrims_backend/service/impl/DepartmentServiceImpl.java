package com.HRIMS.hrims_backend.service.impl;

import com.HRIMS.hrims_backend.dto.DepartmentDto;
import com.HRIMS.hrims_backend.entity.Department;
import com.HRIMS.hrims_backend.mapper.DepartmentMapper;
import com.HRIMS.hrims_backend.repository.DepartmentRepository;
import com.HRIMS.hrims_backend.service.DepartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.HRIMS.hrims_backend.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    @Override
    public DepartmentDto createDepartment(DepartmentDto departmentDto) {
        Department departmentEntity = departmentMapper.toDepartmentEntity(departmentDto);
        Department savedDepartment = departmentRepository.save(departmentEntity);
        return departmentMapper.toDepartmentDto(savedDepartment);
    }

    @Override
    public List<DepartmentDto> getAllDepartments() {
        ObjectMapper mapper = new ObjectMapper();
        List<Department> departmentList = departmentRepository.findAll();
        return departmentList.stream().map(department -> mapper.convertValue(department, DepartmentDto.class)).collect(Collectors.toList());
    }

    @Override
    public DepartmentDto getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id " + id));
        return departmentMapper.toDepartmentDto(department);
    }

    @Override
    public DepartmentDto updateDepartment(Long id, DepartmentDto departmentDetails) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id " + id));

        Department departmentEntity = departmentMapper.toDepartmentEntity(departmentDetails);
        department.setName(departmentEntity.getName());
        department.setCode(departmentEntity.getCode());
        departmentRepository.save(department);
        return departmentMapper.toDepartmentDto(department);
    }

    @Override
    public String deleteDepartmentById(Long id) {
        departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id " + id));
        departmentRepository.deleteById(id);
        return "Successfully deleted Department with id " + id;
    }

    @Override
    public String deleteDepartments() {
        departmentRepository.deleteAll();
        return "Deleted All Departments";
    }
}
