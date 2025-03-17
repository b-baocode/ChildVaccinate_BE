package com.swp.ChildrenVaccine.repository;

import com.swp.ChildrenVaccine.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, String> {
    Optional<Appointment> findByAppId(String appId);
    @Query("SELECT a FROM Appointment a WHERE " +
            "(a.appointmentDate < :nowDate OR " +
            "(a.appointmentDate = :nowDate AND a.appointmentTime < :nowTime)) " +
            "AND a.status <> 'CANCELED'")
    List<Appointment> findExpiredAppointments(
            @Param("nowDate") LocalDate nowDate,
            @Param("nowTime") LocalTime nowTime);
}