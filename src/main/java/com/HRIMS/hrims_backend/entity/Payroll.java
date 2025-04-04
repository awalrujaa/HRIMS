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

    public Payroll() {
    }

    public Payroll(long id, Employee employee, double basicSalary, double dashainAllowance, double bonus, double performanceBonus, double cit, double pf, double ssf, double tax, double others, double netSalary, LocalDateTime pay_date) {
        this.id = id;
        this.employee = employee;
        this.basicSalary = basicSalary;
        this.dashainAllowance = dashainAllowance;
        this.bonus = bonus;
        this.performanceBonus = performanceBonus;
        this.cit = cit;
        this.pf = pf;
        this.ssf = ssf;
        this.tax = tax;
        this.others = others;
        this.netSalary = netSalary;
        this.pay_date = pay_date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
    }

    public double getDashainAllowance() {
        return dashainAllowance;
    }

    public void setDashainAllowance(double dashainAllowance) {
        this.dashainAllowance = dashainAllowance;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public double getPerformanceBonus() {
        return performanceBonus;
    }

    public void setPerformanceBonus(double performanceBonus) {
        this.performanceBonus = performanceBonus;
    }

    public double getCit() {
        return cit;
    }

    public void setCit(double cit) {
        this.cit = cit;
    }

    public double getPf() {
        return pf;
    }

    public void setPf(double pf) {
        this.pf = pf;
    }

    public double getSsf() {
        return ssf;
    }

    public void setSsf(double ssf) {
        this.ssf = ssf;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getOthers() {
        return others;
    }

    public void setOthers(double others) {
        this.others = others;
    }

    public double getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(double netSalary) {
        this.netSalary = netSalary;
    }

    public LocalDateTime getPay_date() {
        return pay_date;
    }

    public void setPay_date(LocalDateTime pay_date) {
        this.pay_date = pay_date;
    }
}
