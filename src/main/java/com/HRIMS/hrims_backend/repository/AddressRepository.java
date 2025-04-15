package com.HRIMS.hrims_backend.repository;

import com.HRIMS.hrims_backend.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    // Additional Query methods if required
    @Query("SELECT a FROM Address a WHERE a.employee.id = :employeeId")
    List<Address> findByEmployeeId(@Param("employeeId") Long employeeId);

    @Modifying
    @Query("DELETE FROM Address a WHERE a.employee.id = :employeeId")
    void deleteByEmployeeId(@Param("employeeId") Long employeeId);

}
