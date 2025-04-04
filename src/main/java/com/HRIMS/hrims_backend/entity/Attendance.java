package com.HRIMS.hrims_backend.entity;

import com.HRIMS.hrims_backend.enums.AttendanceStatus;
import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
    private LocalTime checkIn;
    private LocalTime checkOut;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;

    public Attendance() {
    }

    public Attendance(long id, Employee employee, LocalTime checkIn, LocalTime checkOut, AttendanceStatus status) {
        this.id = id;
        this.employee = employee;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.status = status;
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

    public LocalTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalTime checkIn) {
        this.checkIn = checkIn;
    }

    public LocalTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalTime checkOut) {
        this.checkOut = checkOut;
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    public void setStatus(AttendanceStatus status) {
        this.status = status;
    }
}
