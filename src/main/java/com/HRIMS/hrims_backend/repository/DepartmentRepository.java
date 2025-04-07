package com.HRIMS.hrims_backend.repository;

import com.HRIMS.hrims_backend.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    // Additional Query methods if required
}
