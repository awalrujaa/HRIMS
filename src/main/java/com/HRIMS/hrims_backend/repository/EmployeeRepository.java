package com.HRIMS.hrims_backend.repository;

import com.HRIMS.hrims_backend.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Additional Query methods if required
    Optional<Employee> findByUserName(String userName);
}
