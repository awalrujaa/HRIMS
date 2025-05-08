package com.HRIMS.hrims_backend.repository;

import com.HRIMS.hrims_backend.entity.Role;
import com.HRIMS.hrims_backend.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleType name);
}
