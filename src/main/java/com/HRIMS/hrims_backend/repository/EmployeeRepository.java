package com.HRIMS.hrims_backend.repository;

import com.HRIMS.hrims_backend.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Additional Query methods if required
}
