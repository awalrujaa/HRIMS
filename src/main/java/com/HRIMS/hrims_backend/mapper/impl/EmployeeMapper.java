package com.HRIMS.hrims_backend.mapper.impl;

import com.HRIMS.hrims_backend.dto.DepartmentDto;
import com.HRIMS.hrims_backend.dto.request.EmployeeRequestDto;
import com.HRIMS.hrims_backend.dto.response.AddressDto;
import com.HRIMS.hrims_backend.dto.response.EmployeeResponseDto;
import com.HRIMS.hrims_backend.entity.Employee;
import com.HRIMS.hrims_backend.mapper.IEmployeeMapper;
import com.HRIMS.hrims_backend.mapper.DepartmentMapper;
import com.HRIMS.hrims_backend.mapper.AddressMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeMapper implements IEmployeeMapper {
    private final DepartmentMapper departmentMapper;
    private final AddressMapper addressMapper;


    @Override
    public EmployeeRequestDto toEmployeeRequestDto(Employee employee) {
         return EmployeeRequestDto.builder()
                 .firstName(employee.getFirstName())
                 .middleName(employee.getMiddleName())
                 .lastName(employee.getLastName())
                 .salary(employee.getSalary())
                 .phoneNumber(employee.getPhoneNumber())
                 .email(employee.getEmail())
                 .dateOfBirth(employee.getDateOfBirth())
                 .bloodGroup(employee.getBloodGroup())
                 .dateOfJoining(employee.getDateOfJoining())
                 .departmentId(employee.getDepartment().getId())
                 .street(employee.getAddress().getStreet())
                 .city(employee.getAddress().getCity())
                 .state(employee.getAddress().getState())
                 .zipcode(employee.getAddress().getZipcode())
                 .country(employee.getAddress().getCountry())
                 .build();
    }

    @Override
    public Employee toEmployeeRequestEntity(EmployeeRequestDto employeeRequestDto) {
        return Employee.builder()
                .firstName(employeeRequestDto.getFirstName())
                .middleName(employeeRequestDto.getMiddleName())
                .lastName(employeeRequestDto.getLastName())
                .salary(employeeRequestDto.getSalary())
                .phoneNumber(employeeRequestDto.getPhoneNumber())
                .email(employeeRequestDto.getEmail())
                .dateOfBirth(employeeRequestDto.getDateOfBirth())
                .bloodGroup(employeeRequestDto.getBloodGroup())
                .dateOfJoining(employeeRequestDto.getDateOfJoining())
                .department(departmentMapper.toDepartmentEntity(DepartmentDto.builder().build()))
                .address(addressMapper.toAddressEntity(AddressDto.builder()
                                .street(employeeRequestDto.getStreet())
                        .city(employeeRequestDto.getCity())
                                .state(employeeRequestDto.getState())
                                .zipcode(employeeRequestDto.getZipcode())
                                .country(employeeRequestDto.getCountry())
                        .build()))
                .build();
    }

    @Override
    public Employee toEmployeeEntity(EmployeeResponseDto employeeResponseDto) {

        return Employee.builder()
                .id(employeeResponseDto.getId())
                .firstName(employeeResponseDto.getFirstName())
                .middleName(employeeResponseDto.getMiddleName())
                .lastName(employeeResponseDto.getLastName())
                .salary(employeeResponseDto.getSalary())
                .phoneNumber(employeeResponseDto.getPhoneNumber())
                .email(employeeResponseDto.getEmail())
                .dateOfBirth(employeeResponseDto.getDateOfBirth())
                .bloodGroup(employeeResponseDto.getBloodGroup())
                .dateOfJoining(employeeResponseDto.getDateOfJoining())
                .department(departmentMapper.toDepartmentEntity(employeeResponseDto.getDepartment()))
                .address(addressMapper.toAddressEntity(employeeResponseDto.getAddress()))
                .build();
    }
}
