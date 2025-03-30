package com.swp.ChildrenVaccine.repository;

import com.swp.ChildrenVaccine.entities.Child;
import com.swp.ChildrenVaccine.entities.Schedule;
import com.swp.ChildrenVaccine.enums.ScheduleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, String> {
    List<Schedule> findByChildAndStatus(Child childId, ScheduleStatus status);

    @Query("SELECT MAX(s.scheduleId) FROM Schedule s WHERE s.scheduleId LIKE 'SCHE%'")
    String findMaxAppId();

    List<Schedule> findByChild(Child child);

    @Query("SELECT s FROM Schedule s WHERE s.customer.cusId = :cusId")
    List<Schedule> findByCustomer(@Param("cusId") String cusId);

    @Query("SELECT s FROM Schedule s WHERE s.customer.user.phone = :phoneNumber")
    List<Schedule> findByPhoneNumber(@Param("phoneNumber") String phoneNumber);
}
