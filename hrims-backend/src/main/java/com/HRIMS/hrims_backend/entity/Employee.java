package com.HRIMS.hrims_backend.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String fullName;
    private String userName;
    private String password;    // Password is stored after hashing.
    private String role;
    private double salary;
    private String phoneNumber;
    private String email;
    private String designation;
    private LocalDate dateOfBirth;
    private String bloodGroup;
    private LocalDate dateOfJoining;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    // An employee has temporary and permanent address.
    @OneToMany(mappedBy = "employee")
    private List<Address> addresses;

}
