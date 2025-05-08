package com.HRIMS.hrims_backend.service.impl;

import com.HRIMS.hrims_backend.dto.*;
import com.HRIMS.hrims_backend.entity.Address;
import com.HRIMS.hrims_backend.entity.Department;
import com.HRIMS.hrims_backend.entity.Employee;
import com.HRIMS.hrims_backend.enums.RoleType;
import com.HRIMS.hrims_backend.exception.InvalidPasswordException;
import com.HRIMS.hrims_backend.exception.ResourceNotFoundException;
import com.HRIMS.hrims_backend.mapper.AddressMapper;
import com.HRIMS.hrims_backend.mapper.EmployeeMapper;
import com.HRIMS.hrims_backend.repository.AddressRepository;
import com.HRIMS.hrims_backend.repository.DepartmentRepository;
import com.HRIMS.hrims_backend.repository.EmployeeRepository;
import com.HRIMS.hrims_backend.repository.RoleRepository;
import com.HRIMS.hrims_backend.repository.specification.DepartmentSpecification;
import com.HRIMS.hrims_backend.repository.specification.EmployeeSpecification;
import com.HRIMS.hrims_backend.service.EmployeeService;
import com.HRIMS.hrims_backend.utils.PasswordUtil;
import com.HRIMS.hrims_backend.utils.PasswordValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final AddressRepository addressRepository;
    private final EmployeeMapper employeeMapper;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public ApiResponse<EmployeeResponse> createEmployee(EmployeeRequest employeeRequest) {

        Department department = departmentRepository.findById(employeeRequest.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("No department found for the department id: " + employeeRequest.getDepartmentId()));

        Employee employee = employeeMapper.toEmployeeEntity(employeeRequest);
        if (!PasswordValidator.validatePassword(employee.getPassword())) {
            throw new InvalidPasswordException("Invalid Password.");
        }
        employee.setPassword(PasswordUtil.hashPassword(employee.getPassword()));
        if (Objects.equals(employeeRequest.getRoleType(), "ADMIN")) {
            employee.setRole(roleRepository.findByName(RoleType.ADMIN).get());
            System.out.println("ADMIN");
        } else {
            employee.setRole(roleRepository.findByName(RoleType.USER).get());
            System.out.println("USER");
        }
        employee.setDepartment(department);
        log.info("Saving employee: {}", employee);

        employee.getAddresses().forEach(address -> address.setEmployee(employee));
        employeeRepository.save(employee);
        return new ApiResponse<>(HttpStatus.OK.value(),
                "Created Employee Successfully.", HttpStatus.OK.name(), LocalDateTime.now(),
                employeeMapper.toEmployeeDto(employee), new ArrayList<>());
    }

    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @Override
    public ApiResponse<PaginatedResponse<EmployeeResponse>> getAllEmployees(int pageNum, int pageSize) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<Employee> employees = employeeRepository.findAll(pageable);
        // Map Page<Department> to a list of DepartmentResponse
        List<EmployeeResponse> employeeResponses = employees
                .stream()
                .map(employeeMapper::toEmployeeDto)
                .collect(Collectors.toList());

        // Create a PaginatedResponse
        PaginatedResponse<EmployeeResponse> paginatedData = new PaginatedResponse<>(
                employeeResponses,
                employees.getTotalPages(),
                employees.getTotalElements(),
                employees.getNumber(),
                employees.getSize()
        );
        return new ApiResponse<>(HttpStatus.OK.value(),
                "Employees retrieved Successfully.", HttpStatus.OK.name(), LocalDateTime.now(),
                paginatedData, new ArrayList<>());
    }

    @Transactional
    @Override
    public ApiResponse<EmployeeResponse> getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Employee found with id " + id));

        return new ApiResponse<>(HttpStatus.OK.value(),
                "Employee retrieved successfully.", HttpStatus.OK.name(), LocalDateTime.now(),
                employeeMapper.toEmployeeDto(employee), new ArrayList<>());
    }

    @Override
    public ApiResponse<EmployeeResponse> updateEmployee(Long id, EmployeeRequest employeeDetails) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Employee found with id " + id));


        employee.setFirstName(employeeDetails.getFirstName());
        employee.setMiddleName(employeeDetails.getMiddleName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setFullName();
        employee.setSalary(employeeDetails.getSalary());
        employee.setMobileNumber(employeeDetails.getMobileNumber());
        employee.setEmail(employeeDetails.getEmail());
        employee.setDateOfBirth(employeeDetails.getDateOfBirth());
        employee.setBloodGroup(employeeDetails.getBloodGroup());
        employee.setDateOfJoining(employeeDetails.getDateOfJoining());

        long depId = employeeDetails.getDepartmentId();
        Optional<Department> optionalDepartment = departmentRepository.findById(depId);
        if (optionalDepartment.isEmpty()){
            throw new RuntimeException("Department not found with id: " + depId);
        }
        employee.setDepartmentId(depId);
        employee.setDepartment(departmentRepository.findById(depId).get());

        List<Address> oldAddresses = addressRepository.findByEmployeeId(id);
        List<Address> updatedAddresses = employeeDetails.getAddressList().stream()
                .map(addressDto -> {
                    Address address = AddressMapper.addressMapper.toAddressEntity(addressDto);  // Use the mapper to convert AddressDto to Address
                    address.setEmployee(employee);  // Link the address to the employee
                    return address;
                })
                .collect(Collectors.toList());
        for(Address address: updatedAddresses) {
            addressRepository.save(address);
        }

        for(Address address: oldAddresses){
            addressRepository.delete(address);
        }

        employeeRepository.save(employee);
        return new ApiResponse<>(HttpStatus.OK.value(),
                "Employee updated successfully.", HttpStatus.OK.name(), LocalDateTime.now(),
                employeeMapper.toEmployeeDto(employee), new ArrayList<>());
    }

    @Override
    public ApiResponse<String> deleteEmployee(Long id) {
        employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Employee found with id " + id));

        employeeRepository.deleteById(id);
        return new ApiResponse<>(HttpStatus.NO_CONTENT.value(),
                "Employee Deleted Successfully.", HttpStatus.NO_CONTENT.name(), LocalDateTime.now(),
                "Employee with ID " + id + " deleted successfully.", new ArrayList<>());
    }

    public ApiResponse<PaginatedResponse<EmployeeResponse>> filterEmployees(String email, String middleName, int pageNum, int pageSize) {
        Specification<Employee> specification =
                (Specification<Employee>) EmployeeSpecification.filterEmployee(email, middleName);
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<Employee> employees = employeeRepository.findAll(specification, pageable);

        // Map Page<Department> to a list of DepartmentResponse
        List<EmployeeResponse> employeeResponses = employees
                .stream()
                .map(employeeMapper::toEmployeeDto)
                .collect(Collectors.toList());


        // Create a PaginatedResponse
        PaginatedResponse<EmployeeResponse> paginatedData = new PaginatedResponse<>(
                employeeResponses,
                employees.getTotalPages(),
                employees.getTotalElements(),
                employees.getNumber(),
                employees.getSize()
        );
        return new ApiResponse<>(HttpStatus.OK.value(),
                "Filtered Employee Successfully.", HttpStatus.OK.name(), LocalDateTime.now(),
                paginatedData, new ArrayList<>());
    }
}
