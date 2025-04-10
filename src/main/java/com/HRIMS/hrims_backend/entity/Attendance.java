package com.HRIMS.hrims_backend.entity;

import com.HRIMS.hrims_backend.enums.AttendanceStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;
    private LocalTime checkIn;
    private LocalTime checkOut;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;
}
