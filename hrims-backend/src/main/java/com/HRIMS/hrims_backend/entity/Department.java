package com.HRIMS.hrims_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Id;

import java.util.List;

@Entity
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "department")
    private List<Employee> employees;   // One department can have multiple employees.

    private String departmentName;
    private String departmentCode;
}
