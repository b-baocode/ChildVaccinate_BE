package com.swp.ChildrenVaccine.repository;

import com.swp.ChildrenVaccine.entities.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, String> {
    @Query("SELECT r FROM Record r WHERE r.appointment.appId = :appointmentId")
    List<Record> findByAppointmentId(@Param("appointmentId") String appointmentId);
}