package com.swp.ChildrenVaccine.repository;

import com.swp.ChildrenVaccine.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, String> {

    //Lấy danh sách tất cả lịch hẹn
    List<Appointment> findAll();

    //luư lịch hẹn
    Appointment save(Appointment appointment);

    Optional<Appointment> findByAppId(String appId);

    @Query("SELECT MAX(a.appId) FROM Appointment a WHERE a.appId LIKE 'APP%'")
    String findMaxAppId();
}
