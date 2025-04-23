package com.HRIMS.hrims_backend.repository;

import com.HRIMS.hrims_backend.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    // Additional Query methods if required
    boolean existsByNameAndCode(String name, String code);

    @Query("SELECT d FROM Department d WHERE d.code = :code")
    Optional<Department> findByCode(String code);
}
