package com.HRIMS.hrims_backend.repository;

import com.HRIMS.hrims_backend.entity.Leave;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveRepository extends JpaRepository<Leave, Long> {
    // Additional Query methods if required
}
