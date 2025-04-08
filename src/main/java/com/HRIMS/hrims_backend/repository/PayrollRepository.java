package com.HRIMS.hrims_backend.repository;

import com.HRIMS.hrims_backend.entity.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayrollRepository extends JpaRepository<Payroll, Long> {
    // Additional Query methods if required
}
