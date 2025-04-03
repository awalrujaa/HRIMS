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
    private LocalTime check_in;
    private LocalTime check_out;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;

}
