package com.HRIMS.hrims_backend.repository;

import com.HRIMS.hrims_backend.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    // Additional Query methods if required
    Optional<Attendance> findByEmployeeIdAndDate(@Param("employeeId") Long employeeId, @Param("date") LocalDate date);
}
