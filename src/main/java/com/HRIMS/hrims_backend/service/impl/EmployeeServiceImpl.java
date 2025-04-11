package com.HRIMS.hrims_backend.service.impl;

import com.HRIMS.hrims_backend.dto.request.EmployeeRequestDto;
import com.HRIMS.hrims_backend.dto.response.EmployeeResponseDto;
import com.HRIMS.hrims_backend.dto.response.AddressDto;
import com.HRIMS.hrims_backend.dto.DepartmentDto;
import com.HRIMS.hrims_backend.entity.Address;
import com.HRIMS.hrims_backend.entity.Department;
import com.HRIMS.hrims_backend.entity.Employee;
import com.HRIMS.hrims_backend.mapper.impl.AddressMapper;
import com.HRIMS.hrims_backend.mapper.impl.DepartmentMapper;
import com.HRIMS.hrims_backend.mapper.impl.EmployeeMapper;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final AddressRepository addressRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeMapper employeeMapper;

    /**
     * Validation:
     *
     * Employee Save
     * Address Save
     * Return Response
     * @param employeeRequestDto
     * @return
     */
    @Override
    @Transactional
    public EmployeeRequestDto createEmployee(EmployeeRequestDto employeeRequestDto) {
        Optional<Department> deptOpt = departmentRepository.findById(employeeRequestDto.getDepartmentId());
        if(deptOpt.isEmpty()) {
            log.error("No department found for the department id: {}", employeeRequestDto.getDepartmentId());
            throw new RuntimeException("Invalid department found");
        }

        Employee employee = employeeMapper.toEmployeeRequestEntity(employeeRequestDto);
        employee.setDepartment(deptOpt.get());

        log.info("Saving employee: {}", employee);
        employeeRepository.save(employee);
        return employeeMapper.toEmployeeRequestDto(employee);

    }

    @Transactional
    @Override
    public List<EmployeeRequestDto> getAllEmployees() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map(employeeMapper::toEmployeeRequestDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public EmployeeRequestDto getEmployeeById(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if(optionalEmployee.isEmpty()){
            throw new RuntimeException("No employee with id " + id);
        }
        return employeeMapper.toEmployeeRequestDto(optionalEmployee.get());
    }

    @Override
    public EmployeeRequestDto updateEmployee(Long id, EmployeeRequestDto employeeDetails) {
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

    // id of address should be original
        long addId = employee.getAddress().getAddressId();
        Optional<Address> optionalAddress = addressRepository.findById(addId);
        if (optionalAddress.isEmpty()){
            throw new RuntimeException("Address not found with id: " + addId);
        }
        Address address = optionalAddress.get();
        address.setStreet(employeeDetails.getStreet());
        address.setCity(employeeDetails.getCity());
        address.setState(employeeDetails.getState());
        address.setZipcode(employeeDetails.getZipcode());
        address.setCountry(employeeDetails.getCountry());
        System.out.println(employee);
        employeeRepository.save(employee);
        return employeeMapper.toEmployeeRequestDto(employee);
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
