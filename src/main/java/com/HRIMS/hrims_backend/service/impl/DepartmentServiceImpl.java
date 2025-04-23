package com.HRIMS.hrims_backend.service.impl;

import com.HRIMS.hrims_backend.dto.DepartmentRequest;
import com.HRIMS.hrims_backend.dto.DepartmentResponse;
import com.HRIMS.hrims_backend.dto.PaginatedResponse;
import com.HRIMS.hrims_backend.dto.ApiResponse;
import com.HRIMS.hrims_backend.entity.Department;
import com.HRIMS.hrims_backend.exception.ResourceAlreadyExistsException;
import com.HRIMS.hrims_backend.mapper.DepartmentMapper;
import com.HRIMS.hrims_backend.repository.DepartmentRepository;
import com.HRIMS.hrims_backend.service.DepartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.HRIMS.hrims_backend.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    @Override
    public ApiResponse<DepartmentResponse> createDepartment(DepartmentRequest departmentRequest) {
        if (departmentRepository.existsByNameAndCode(departmentRequest.getName(), departmentRequest.getCode())) {
            throw new ResourceAlreadyExistsException("Department already exists.");
        }

        try {
            Department departmentEntity = departmentMapper.toDepartmentEntity(departmentRequest);
            Department savedDepartment = departmentRepository.save(departmentEntity);
            return new ApiResponse<>(HttpStatus.OK.value(),
                    "Created Department Successfully.", HttpStatus.OK.name(), LocalDateTime.now(),
                    departmentMapper.toDepartmentDTO(savedDepartment), new ArrayList<>());
        } catch (DataIntegrityViolationException e) {
            throw new ResourceAlreadyExistsException("Department already exists.");
        }
    }

    @Override
    public ApiResponse<PaginatedResponse<DepartmentResponse>> getAllDepartments(int pageNum, int pageSize) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<Department> departments = departmentRepository.findAll(pageable);
        // Map Page<Department> to a list of DepartmentResponse
        List<DepartmentResponse> departmentResponses = departments
                .stream()
                .map(departmentMapper::toDepartmentDTO)
                .collect(Collectors.toList());

        // Create a PaginatedResponse
        PaginatedResponse<DepartmentResponse> paginatedData = new PaginatedResponse<>(
                departmentResponses,
                departments.getTotalPages(),
                departments.getTotalElements(),
                departments.getNumber(),
                departments.getSize()
        );
        return new ApiResponse<>(HttpStatus.OK.value(),
                "Retrieved Department Successfully.", HttpStatus.OK.name(), LocalDateTime.now(),
                paginatedData, new ArrayList<>());
    }

    @Override
    public ApiResponse<DepartmentResponse> getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id " + id));
        return new ApiResponse<>(HttpStatus.OK.value(),
                "Department retrieved successfully.", HttpStatus.OK.name(), LocalDateTime.now(),
                departmentMapper.toDepartmentDTO(department), new ArrayList<>());
    }

    @Override
    public ApiResponse<DepartmentResponse> updateDepartment(Long id, DepartmentRequest departmentRequest) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id " + id));

        Department departmentEntity = departmentMapper.toDepartmentEntity(departmentRequest);
        if (departmentRepository.existsByNameAndCode(departmentRequest.getName(), departmentRequest.getCode())) {
            throw new ResourceAlreadyExistsException("Department already exists.");
        }

        try {
            department.setName(departmentEntity.getName());
            department.setCode(departmentEntity.getCode());
            departmentRepository.save(department);
            return new ApiResponse<>(HttpStatus.OK.value(),
                    "Department updated successfully.", HttpStatus.OK.name(), LocalDateTime.now(),
                    departmentMapper.toDepartmentDTO(department), new ArrayList<>());
        } catch (DataIntegrityViolationException e) {
            throw new ResourceAlreadyExistsException("Department already exists");
        }

    }

    @Override
    public ApiResponse<String> deleteDepartmentById(Long id) {
        departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id " + id));
        departmentRepository.deleteById(id);
        return new ApiResponse<>(HttpStatus.NO_CONTENT.value(),
                "Department Deleted Successfully.", HttpStatus.NO_CONTENT.name(), LocalDateTime.now(),
                "Department with ID " + id + " deleted successfully.", new ArrayList<>());
    }
}
