package com.HRIMS.hrims_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;
    private String middleName;
    private String lastName;
    private String fullName;
//    private String userName;    // For Login
//    private String password;    // Password is stored only after hashing.
//    private String role;
    private double salary;
    private String phoneNumber;
    private String email;
//    private String designation;
    private LocalDate dateOfBirth;
    private String bloodGroup;
    private LocalDate dateOfJoining;

    @Column(name = "department_id")
    private Long departmentId;

    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Department department;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    // Automatically set full name before saving
    @PrePersist
    @PreUpdate
    public void setFullName() {
        this.fullName = String.join(" ",
                firstName != null ? firstName : "",
                middleName != null && !middleName.isBlank() ? middleName : "",
                lastName != null ? lastName : ""
        ).trim().replaceAll(" +", " "); // remove extra spaces
    }
}
