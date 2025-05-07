package com.HRIMS.hrims_backend.service.impl;

import com.HRIMS.hrims_backend.dto.DepartmentRequest;
import com.HRIMS.hrims_backend.dto.DepartmentResponse;
import com.HRIMS.hrims_backend.dto.PaginatedResponse;
import com.HRIMS.hrims_backend.dto.ApiResponse;
import com.HRIMS.hrims_backend.entity.Department;
import com.HRIMS.hrims_backend.exception.ResourceAlreadyExistsException;
import com.HRIMS.hrims_backend.helper.ExcelHelper;
import com.HRIMS.hrims_backend.mapper.DepartmentMapper;
import com.HRIMS.hrims_backend.repository.DepartmentRepository;
import com.HRIMS.hrims_backend.repository.specification.DepartmentSpecification;
import com.HRIMS.hrims_backend.service.DepartmentService;
import com.HRIMS.hrims_backend.service.ExcelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.HRIMS.hrims_backend.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;
    private final ExcelService excelService;

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
    public ApiResponse<List<DepartmentResponse>> bulkUploadDepartments(List<DepartmentRequest> departments) {
        for(DepartmentRequest departmentRequest: departments) {
            if (departmentRepository.existsByNameAndCode(departmentRequest.getName(), departmentRequest.getCode())) {
                throw new ResourceAlreadyExistsException("Department already exists.");
            }
        }

        List<Department> departmentEntityList = departments
                .stream()
                .map(departmentMapper::toDepartmentEntity)
                .collect(Collectors.toList());

        departmentRepository.saveAll(departmentEntityList);

        List<DepartmentResponse> savedDepartmentList = departmentEntityList
                .stream()
                .map(departmentMapper::toDepartmentDTO)
                .collect(Collectors.toList());
        return new ApiResponse<>(HttpStatus.OK.value(),
                        "Created Department Successfully.", HttpStatus.OK.name(), LocalDateTime.now(),
                        savedDepartmentList, new ArrayList<>());

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
        departmentEntity.setId(id);

        if (department.equals(departmentEntity)) {
            return new ApiResponse<>(HttpStatus.OK.value(),
                    "Department updated successfully.", HttpStatus.OK.name(), LocalDateTime.now(),
                    departmentMapper.toDepartmentDTO(department), new ArrayList<>());
        }
//            dept1.equals(dept2)) {

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

    @Override
    public ApiResponse<PaginatedResponse<DepartmentResponse>> filterDepartmentsPagination(String searchText, String name,
                                                                                          String code,
                                                                                          String createdBy, String updatedBy,
                                                                                          int pageNum, int pageSize) {

        Specification<Department> specification =
                (Specification<Department>) DepartmentSpecification.filterDepartment(searchText, name, code, createdBy, updatedBy);
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<Department> departments = departmentRepository.findAll(specification, pageable);

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
                "Filtered Department Successfully.", HttpStatus.OK.name(), LocalDateTime.now(),
                paginatedData, new ArrayList<>());
    }

    public ApiResponse<String> uploadFile(MultipartFile file) {
        String message = "";

        if (ExcelHelper.hasExcelFormet(file)) {
            try {
                excelService.save(file);

                message = "Uploaded file successfully: " + file.getOriginalFilename();

                return new ApiResponse<>(HttpStatus.OK.value(),
                        "Uploaded file successfully", HttpStatus.OK.name(), LocalDateTime.now(),
                        message, new ArrayList<>());

            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename();
                return new ApiResponse<>(HttpStatus.EXPECTATION_FAILED.value(),
                        "Could not upload the file", HttpStatus.EXPECTATION_FAILED.name(), LocalDateTime.now(),
                        message, new ArrayList<>());
            }
        }

        message = "Upload an excel file.";
        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
                "Only excel format files supported.", HttpStatus.BAD_REQUEST.name(), LocalDateTime.now(),
                message, new ArrayList<>());
    }



}
