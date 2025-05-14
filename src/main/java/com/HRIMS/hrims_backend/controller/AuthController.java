//package com.HRIMS.hrims_backend.controller;
//
//import com.HRIMS.hrims_backend.dto.*;
//import com.HRIMS.hrims_backend.entity.Employee;
//import com.HRIMS.hrims_backend.repository.EmployeeRepository;
//import com.HRIMS.hrims_backend.service.EmployeeService;
//import com.HRIMS.hrims_backend.service.impl.AuthenticationServiceImpl;
//import com.HRIMS.hrims_backend.service.impl.JwtServiceImpl;
//import jakarta.validation.Valid;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.bind.annotation.CrossOrigin;
//
//
//@RestController
//@CrossOrigin(origins = "http://localhost:4200")
//@RequestMapping("/api/auth")
//@Data
//public class AuthController {
//
//    private final EmployeeRepository employeeRepository;
//    private final EmployeeService employeeService;
//    private final JwtServiceImpl jwtService;
//
//    private final AuthenticationServiceImpl authenticationService;
//
//
//
//    public AuthController(EmployeeRepository employeeRepository, EmployeeService employeeService,
//                          JwtServiceImpl jwtService, AuthenticationServiceImpl authenticationService) {
//        this.employeeRepository = employeeRepository;
//        this.employeeService = employeeService;
//        this.jwtService = jwtService;
//        this.authenticationService = authenticationService;
//    }
//
////    @PostMapping("/register")
////    public ApiResponse<EmployeeResponse> createEmployee(@RequestBody @Valid EmployeeRequest employeeRequest){
////        return employeeService.createEmployee(employeeRequest);
////    }
//
//
//    @PostMapping("/login")
//    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest loginUserDto) {
//        Employee authenticatedUser = authenticationService.authenticate(loginUserDto);
//        String jwtToken = jwtService.generateToken(authenticatedUser);
//
//        LoginResponse loginResponse = new LoginResponse();
//        loginResponse.setToken(jwtToken);
//        loginResponse.setExpiresIn(jwtService.getExpirationTime());
//
//        return ResponseEntity.ok(loginResponse);
//    }
//}
