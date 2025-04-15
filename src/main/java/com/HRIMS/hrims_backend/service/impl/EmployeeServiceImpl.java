package com.HRIMS.hrims_backend.service.impl;

import com.HRIMS.hrims_backend.dto.AddressDto;
import com.HRIMS.hrims_backend.entity.Address;
import com.HRIMS.hrims_backend.dto.EmployeeDto;
import com.HRIMS.hrims_backend.entity.Department;
import com.HRIMS.hrims_backend.entity.Employee;
import com.HRIMS.hrims_backend.mapper.AddressMapper;
import com.HRIMS.hrims_backend.mapper.EmployeeMapper;
import com.HRIMS.hrims_backend.repository.AddressRepository;
import com.HRIMS.hrims_backend.repository.DepartmentRepository;
import com.HRIMS.hrims_backend.repository.EmployeeRepository;
import com.HRIMS.hrims_backend.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    /**
     * Validation:
     *
     * Employee Save
     * Address Save
     * Return Response
     * @param employeeDto
     * @return
     */
    @Override
    @Transactional
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Optional<Department> optDepartment = departmentRepository.findById(employeeDto.getDepartmentId());
        if(optDepartment.isEmpty()) {
            log.error("No department found for the department id: {}", employeeDto.getDepartmentId());
            throw new RuntimeException("Invalid department found");
        }

        Employee employee = employeeMapper.toEmployeeEntity(employeeDto);
        employee.setDepartment(optDepartment.get());
        log.info("Saving employee: {}", employee);

        employee.getAddresses().forEach(address -> address.setEmployee(employee));
        employeeRepository.save(employee);
        return employeeMapper.toEmployeeDto(employee);
    }

    @Transactional
    @Override
    public List<EmployeeDto> getAllEmployees() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map(employeeMapper::toEmployeeDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public EmployeeDto getEmployeeById(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if(optionalEmployee.isEmpty()){
            throw new RuntimeException("No employee with id " + id);
        }
        return employeeMapper.toEmployeeDto(optionalEmployee.get());
    }

    @Override
    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDetails) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isEmpty()){
            throw new RuntimeException("Employee not found with id: " + id);
        }
        Employee employee = optionalEmployee.get();
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setMiddleName(employeeDetails.getMiddleName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setFullName();
        employee.setSalary(employeeDetails.getSalary());
        employee.setPhoneNumber(employeeDetails.getPhoneNumber());
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
        return employeeMapper.toEmployeeDto(employee);
    }

    @Override
    public String deleteEmployee(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isEmpty()){
            return "Employee doesn't exist with id: " + id;
        }
        employeeRepository.deleteById(id);
        return "Success";
    }

    @Override
    public String deleteAllEmployees() {
        employeeRepository.deleteAll();
        return "Successfully Deleted All Employees";
    }
}
