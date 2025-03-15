package com.swp.ChildrenVaccine.service;

import com.swp.ChildrenVaccine.entities.Appointment;
import com.swp.ChildrenVaccine.entities.Customer;
import com.swp.ChildrenVaccine.entities.User;
import com.swp.ChildrenVaccine.repository.AppointmentRepository;
import com.swp.ChildrenVaccine.repository.ChildRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private  EmailService emailService;

    //Get customer information using session after login

    //Get all child list of the customer

    //Get all vaccine list

    //Get all package list

//    public void createAppointment(String customerId, String childId, String vaccineId, String packageId, LocalDate appointmentDate, LocalTime appointmentTime) throws Exception {
//
//        // Validate that either a vaccine or a package is selected, but not both
//        if ((vaccineId == null && packageId == null) || (vaccineId != null && packageId != null)) {
//            throw new Exception("Either vaccine or package must be selected, but not both");
//        }
//
//        // Create and save the appointment
//        Appointment appointment = new Appointment();
//        appointment.getCustomerId();
//        appointment.setChildId(childId);
//        appointment.setVaccineId(vaccineId);
//        appointment.setPackageId(packageId);
//        appointment.setAppointmentDate(appointmentDate);
//        appointment.setAppointmentTime(appointmentTime);
//        appointment.setStatus(Appointment.Status.CONFIRMED);
//
//        appointmentRepository.save(appointment);
//    }

    public List<Appointment> findAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointment findByAppId(String appId) {
        return appointmentRepository.findByAppId(appId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy cuộc hẹn với ID: " + appId));
    }

    public boolean sendAppointmentEmail(String appointmentId) {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(appointmentId);

        if (optionalAppointment.isPresent()) {
            Appointment appointment = optionalAppointment.get();
            Customer customer = appointment.getCustomerId();
            User user = customer.getUser();

            String email = user.getEmail();
            String subject = "Xác nhận cuộc hẹn tại phòng khám VNVC";

            String body = "<h3>Xin chào " + user.getFullName() + ",</h3>"
                    + "<p>Bạn có một cuộc hẹn được xác nhận với thông tin sau:</p>"
                    + "<ul>"
                    + "<li><b>Tên Trẻ:</b> " + appointment.getChildId().getFullName() + "</li>"
                    + "<li><b>Ngày hẹn:</b> " + appointment.getAppointmentDate() + "</li>"
                    + "<li><b>Giờ hẹn:</b> " + appointment.getAppointmentTime() + "</li>"
                    + (appointment.getVaccineId() != null ? "<li><b>Vaccine:</b> " + appointment.getVaccineId().getName() + "</li>" : "")
                    + (appointment.getPackageId() != null ? "<li><b>Gói Vaccine:</b> " + appointment.getPackageId().getName() + "</li>" : "")
                    + "</ul>"
                    + "<p>Vui lòng đến đúng giờ!</p>"
                    + "<br><p>Trân trọng,<br>Phòng khám VNVC</p>";

            emailService.sendAppointmentNotification(email, subject, body);
            return true;
        } else {
            return false;
        }
    }



}