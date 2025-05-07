package com.HRIMS.hrims_backend.repository;

import com.HRIMS.hrims_backend.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
    // Additional Query methods if required
    Optional<Employee> findByUserName(String userName);
    Optional<Employee> findByEmail(String email);


    Page<Employee> findAll(Specification<Employee> specification, Pageable pageable);
}
