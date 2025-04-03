package com.HRIMS.hrims_backend.entity;

import com.HRIMS.hrims_backend.enums.LeaveStatus;
import com.HRIMS.hrims_backend.enums.LeaveType;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Leave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Enumerated(EnumType.STRING)
    private LeaveType leaveType;

    private LocalDate startDate;
    private LocalDate end_date;

    @Enumerated(EnumType.STRING)
    private LeaveStatus leaveStatus;


}
