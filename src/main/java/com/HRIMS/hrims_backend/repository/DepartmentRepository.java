package com.HRIMS.hrims_backend.repository;

import com.HRIMS.hrims_backend.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long>, JpaSpecificationExecutor<Department> {
    // Additional Query methods if required
    boolean existsByNameAndCode(String name, String code);

    @Query("SELECT d FROM Department d WHERE d.code = :code")
    Optional<Department> findByCode(String code);
    Page<Department> findAll(Specification<Department> specification, Pageable pageable);

}
