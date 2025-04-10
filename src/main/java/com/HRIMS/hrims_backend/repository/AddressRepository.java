package com.HRIMS.hrims_backend.repository;

import com.HRIMS.hrims_backend.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    // Additional Query methods if required
}
