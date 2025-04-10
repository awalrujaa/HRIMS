package com.HRIMS.hrims_backend.repository;

import com.HRIMS.hrims_backend.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    // Additional Query methods if required
}
