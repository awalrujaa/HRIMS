package com.HRIMS.hrims_backend.repository;

import com.HRIMS.hrims_backend.entity.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, Long> {
    // Additional Query methods if required
    @Query("SELECT l FROM Leave l WHERE l.startDate >= :startDate AND l.endDate <= :endDate AND l.leaveStatus != 'CANCELLED'")
    List<Leave> findLeavesBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
