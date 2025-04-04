package com.HRIMS.hrims_backend.serviceImplementation;

import com.HRIMS.hrims_backend.entity.Address;
import com.HRIMS.hrims_backend.entity.Department;
import com.HRIMS.hrims_backend.entity.Employee;
import com.HRIMS.hrims_backend.repository.AddressRepository;
import com.HRIMS.hrims_backend.repository.DepartmentRepository;
import com.HRIMS.hrims_backend.repository.EmployeeRepository;
import com.HRIMS.hrims_backend.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

//    @Autowired
//    private DepartmentRepository departmentRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Employee createEmployee(Employee employee) {

        employee.setFirstName(employee.getFirstName());
        employee.setMiddleName(employee.getMiddleName());
        employee.setLastName(employee.getLastName());
        employee.setFullName();
        employee.setSalary(employee.getSalary());
        employee.setPhoneNumber(employee.getPhoneNumber());
        employee.setEmail(employee.getEmail());
        employee.setDateOfBirth(employee.getDateOfBirth());
        employee.setBloodGroup(employee.getBloodGroup());
        employee.setDateOfJoining(employee.getDateOfJoining());

        // Create and save address
//        List<Address> addresses = new ArrayList<>();
//        for(Address addressDetails: employee.getAddresses()) {
            Address address =  new Address();
            address.setStreet(employee.getAddresses().getStreet());
            address.setCity(employee.getAddresses().getCity());
            address.setState(employee.getAddresses().getState());
            address.setZipcode(employee.getAddresses().getZipcode());
            address.setCountry(employee.getAddresses().getCountry());
////            address.setEmployee((employee));

//        }

        employee.setAddresses(employee.getAddresses());


        return employeeRepository.save(employee);
    }
}
