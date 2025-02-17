package com.swp.ChildrenVaccine.repository;

import com.swp.ChildrenVaccine.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Lấy danh sách lịch hẹn của một khách hàng
    List<Appointment> findByCustomerId(String customerId);

    // Lấy danh sách lịch hẹn của một đứa trẻ
    List<Appointment> findByChildId(String childId);

    // Tìm lịch hẹn theo ngày
    List<Appointment> findByAppointmentDate(LocalDate appointmentDate);

    //Lấy danh sách tất cả lịch hẹn
    List<Appointment> findAll();

    // Kiểm tra xem một khách hàng đã đặt lịch cho một ngày cụ thể chưa
    boolean existsByCustomerIdAndAppointmentDate(String customerId, LocalDate appointmentDate);

    @Query("SELECT MAX(a.appId) FROM Appointment a")
    String findMaxAppId();

    //luư lịch hẹn
    Appointment save(Appointment appointment);
}
