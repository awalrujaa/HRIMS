package com.HRIMS.hrims_backend.serviceImplementation;

import com.HRIMS.hrims_backend.dto.response.DepartmentDto;
import com.HRIMS.hrims_backend.entity.Department;
import com.HRIMS.hrims_backend.mapper.DepartmentMapper;
import com.HRIMS.hrims_backend.repository.DepartmentRepository;
import com.HRIMS.hrims_backend.service.DepartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    public DepartmentServiceImpl (DepartmentRepository departmentRepository, DepartmentMapper departmentMapper){
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
    }

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
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        // If the department is not found, throw an exception
        if (optionalDepartment.isEmpty()) {
            throw new RuntimeException("Department not found with id " + id);
        }
        Department department = optionalDepartment.get();
        return departmentMapper.toDepartmentDto(department);
    }

    @Override
    public DepartmentDto updateDepartment(Long id, DepartmentDto departmentDetails) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        // If the department is not found, throw an exception
        if (optionalDepartment.isEmpty()) {
            throw new RuntimeException("Department not found with id " + id);
        }

        Department departmentEntity = departmentMapper.toDepartmentEntity(departmentDetails);
        // Get the existing department from the optional object
        Department department = optionalDepartment.get();
        department.setDepartmentName(departmentEntity.getDepartmentName());
        department.setDepartmentCode(departmentEntity.getDepartmentCode());
        departmentRepository.save(department);
        return departmentMapper.toDepartmentDto(department);
    }

    @Override
    public String deleteDepartmentById(Long id) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        // If the department is not found, throw an exception (or handle it as needed)
        if (optionalDepartment.isEmpty()) {
            return "Department not found with id " + id;
        }
        departmentRepository.deleteById(id);
        return "Success";
    }

    @Override
    public String deleteDepartments() {
        departmentRepository.deleteAll();
        return "Deleted All Departments";
    }

}
