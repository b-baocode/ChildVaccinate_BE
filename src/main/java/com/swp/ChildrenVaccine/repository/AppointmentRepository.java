package com.swp.ChildrenVaccine.repository;

import com.swp.ChildrenVaccine.dto.response.AppointmentFeedbackDTO;
import com.swp.ChildrenVaccine.entities.Appointment;
import com.swp.ChildrenVaccine.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    //Lấy danh sách tất cả lịch hẹn
    List<Appointment> findAll();

    //luư lịch hẹn
    Appointment save(Appointment appointment);

    Optional<Appointment> findByAppId(String appId);

    @Query("SELECT MAX(a.appId) FROM Appointment a WHERE a.appId LIKE 'APP%'")
    String findMaxAppId();

    @Query("SELECT a FROM Appointment a WHERE a.customer.cusId = :cusId AND a.status = 'COMPLETED' AND a.appId NOT IN (SELECT r.appointment.appId FROM RatingFeedback r)")
    List<Appointment> findCompletedAppointmentsWithoutFeedback(@Param("cusId") String cusId);

    @Query("SELECT a FROM Appointment a WHERE a.child.childId = :childId")
    List<Appointment> findByChildId(@Param("childId") String childId);

    @Query("SELECT a FROM Appointment a WHERE a.customer.cusId = :cusId")
    List<Appointment> findByCustomerId(@Param("cusId") String cusId);
}
