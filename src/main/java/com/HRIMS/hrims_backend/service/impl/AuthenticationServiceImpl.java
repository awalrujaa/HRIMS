//package com.HRIMS.hrims_backend.service.impl;
//
//import com.HRIMS.hrims_backend.dto.LoginRequest;
//import com.HRIMS.hrims_backend.entity.Employee;
//import com.HRIMS.hrims_backend.repository.EmployeeRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class AuthenticationServiceImpl {
//    private final EmployeeRepository employeeRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final AuthenticationManager authenticationManager;
//
//    public Employee authenticate(LoginRequest input) {
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        input.getEmail(),
//                        input.getPassword()
//                )
//        );
//
//        return employeeRepository.findByEmail(input.getEmail())
//                .orElseThrow();
//    }
//}
