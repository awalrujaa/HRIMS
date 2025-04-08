package com.HRIMS.hrims_backend.serviceImplementation;

import com.HRIMS.hrims_backend.dto.response.EmployeeResponseDto;
import com.HRIMS.hrims_backend.entity.Address;
import com.HRIMS.hrims_backend.entity.Department;
import com.HRIMS.hrims_backend.entity.Employee;
import com.HRIMS.hrims_backend.repository.AddressRepository;
import com.HRIMS.hrims_backend.repository.DepartmentRepository;
import com.HRIMS.hrims_backend.repository.EmployeeRepository;
import com.HRIMS.hrims_backend.service.EmployeeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public Employee createEmployee(Employee employee) {

        employee.setFirstName(employee.getFirstName());
        employee.setMiddleName(employee.getMiddleName());
        employee.setLastName(employee.getLastName());
//        employee.setFullName(employee.getFullName());
        employee.setSalary(employee.getSalary());
        employee.setPhoneNumber(employee.getPhoneNumber());
        employee.setEmail(employee.getEmail());
        employee.setDateOfBirth(employee.getDateOfBirth());
        employee.setBloodGroup(employee.getBloodGroup());
        employee.setDateOfJoining(employee.getDateOfJoining());

        employee.setDepartment(employee.getDepartment());

        Address address =  new Address();
        address.setStreet(employee.getAddress().getStreet());
        address.setCity(employee.getAddress().getCity());
        address.setState(employee.getAddress().getState());
        address.setZipcode(employee.getAddress().getZipcode());
        address.setCountry(employee.getAddress().getCountry());

        employee.setAddress(employee.getAddress());


        return employeeRepository.save(employee);
    }

    @Transactional
    @Override
    public List<EmployeeResponseDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();

        return employees.stream().map(employee -> EmployeeResponseDto
                .builder()
                .firstName(employee.getFirstName())
                .middleName(employee.getMiddleName())
                .lastName(employee.getLastName())
                .fullName(employee.getFullName())
                .salary(employee.getSalary())
                .phoneNumber(employee.getPhoneNumber())
                .email(employee.getEmail())
                .dateOfBirth(employee.getDateOfBirth())
                .bloodGroup(employee.getBloodGroup())
                .dateOfJoining(employee.getDateOfJoining())
                .department(EmployeeResponseDto.DepartmentResponseDto
                        .builder()
                        .departmentName(employee.getDepartment().getDepartmentName())
                        .departmentCode(employee.getDepartment().getDepartmentCode())
                        .build())
                .address(EmployeeResponseDto.AddressResponseDto
                        .builder()
                        .state(employee.getAddress().getState())
                        .build())
                .build()).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public Employee updateEmployee(Long id, Employee employeeDetails) {
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

        long depId = employeeDetails.getDepartment().getId();
        System.out.println(depId);
        Optional<Department> optionalDepartment = departmentRepository.findById(depId);
        if (optionalDepartment.isEmpty()){
            throw new RuntimeException("Department not found with id: " + depId);
        }
        employee.setDepartment(employeeDetails.getDepartment());

        // id of address should be original
        long addId = employee.getAddress().getId();
        Optional<Address> optionalAddress = addressRepository.findById(addId);
        if (optionalAddress.isEmpty()){
            throw new RuntimeException("Address not found with id: " + addId);
        }
        Address address = optionalAddress.get();
        address.setStreet(employeeDetails.getAddress().getStreet());
        address.setCity(employeeDetails.getAddress().getCity());
        address.setState(employeeDetails.getAddress().getState());
        address.setZipcode(employeeDetails.getAddress().getZipcode());
        address.setCountry(employeeDetails.getAddress().getCountry());
        return employeeRepository.save(employee);
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
        return "";
    }
}
