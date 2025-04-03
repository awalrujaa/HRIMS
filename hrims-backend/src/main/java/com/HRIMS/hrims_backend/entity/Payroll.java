package com.HRIMS.hrims_backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Payroll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private double basicSalary;
    private double dashainAllowance;
    private double bonus;
    private double performanceBonus;
    private double cit;
    private double pf;
    private double ssf;
    private double tax;
    private double others;

    private double netSalary;
    private LocalDateTime pay_date;
}
