package com.HRIMS.hrims_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Payroll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
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
    private LocalDateTime payDate;
}
