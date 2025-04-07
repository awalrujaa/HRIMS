package com.HRIMS.hrims_backend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;

//    @Column(name = "department_id")
//    private Long departmentId;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "addressId", nullable = false)
    private Address address;
//    private List<Address> addresses;

    public Employee() {
    }

    public Employee(long id, String firstName, String middleName, String lastName, String fullName, String userName, String password, String role, double salary, String phoneNumber, String email, String designation, LocalDate dateOfBirth, String bloodGroup, LocalDate dateOfJoining, Department department, Address addresses) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.fullName = fullName;
//        this.userName = userName;
//        this.password = password;
//        this.role = role;
        this.salary = salary;
        this.phoneNumber = phoneNumber;
        this.email = email;
//        this.designation = designation;
        this.dateOfBirth = dateOfBirth;
        this.bloodGroup = bloodGroup;
        this.dateOfJoining = dateOfJoining;
        this.department = department;
        this.address = addresses;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

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

//    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getRole() {
//        return role;
//    }
//
//    public void setRole(String role) {
//        this.role = role;
//    }
//
    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public String getDesignation() {
//        return designation;
//    }
//
//    public void setDesignation(String designation) {
//        this.designation = designation;
//    }
//
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public LocalDate getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(LocalDate dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Address getAddresses() {
        return address;
    }

    public void setAddresses(Address addresses) {
        this.address = addresses;
    }
}
