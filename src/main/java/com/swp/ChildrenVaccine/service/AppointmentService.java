package com.swp.ChildrenVaccine.service;

import com.swp.ChildrenVaccine.dto.request.RescheduleAppointmentRequest;
import com.swp.ChildrenVaccine.entities.Appointment;
import com.swp.ChildrenVaccine.entities.Customer;
import com.swp.ChildrenVaccine.entities.User;
import com.swp.ChildrenVaccine.enums.AppStatus;
import com.swp.ChildrenVaccine.enums.MailNoticeStatus;
import com.swp.ChildrenVaccine.repository.AppointmentRepository;
import com.swp.ChildrenVaccine.repository.ChildRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public void sendReminderEmailsForUpcomingAppointments(String cusId) {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        List<Appointment> appointments = appointmentRepository.findByCustomerId(cusId);
        List<Appointment> upcomingAppointments = appointments.stream()
                .filter(a -> a.getAppointmentDate().isEqual(tomorrow) && a.getStatus() == AppStatus.CONFIRMED)
                .collect(Collectors.toList());

        for (Appointment appointment : upcomingAppointments) {
            sendAppointmentEmail(appointment);
        }
    }

    public String sendAppointmentEmail(Appointment appointment) {
        if (appointment.getMailNotice() == MailNoticeStatus.SENDED) {
            return "Appointment " + appointment.getAppId() + " email already sent.";
        }

        Customer customer = appointment.getCustomer();
        User user = customer.getUser();
        String email = user.getEmail();
        String subject = "Appointment Reminder at VNVC Clinic";

        String body = "<h3>Hello " + user.getFullName() + ",</h3>"
                + "<p>You have a confirmed appointment with the following details:</p>"
                + "<ul>"
                + "<li><b>Child Name:</b> " + appointment.getChild().getFullName() + "</li>"
                + "<li><b>Appointment Date:</b> " + appointment.getAppointmentDate() + "</li>"
                + "<li><b>Appointment Time:</b> " + appointment.getAppointmentTime() + "</li>"
                + (appointment.getVaccine() != null ? "<li><b>Vaccine:</b> " + appointment.getVaccine().getName() + "</li>" : "")
                + "</ul>"
                + "<p>Please arrive on time!</p>"
                + "<br><p>Regards,<br>VNVC Clinic</p>";

        emailService.sendAppointmentNotification(email, subject, body);

        appointment.setMailNotice(MailNoticeStatus.SENDED);
        appointmentRepository.save(appointment);

        return "Appointment " + appointment.getAppId() + " email sent successfully.";
    }

    public void checkAndCancelExpiredAppointments() {
        LocalDateTime now = LocalDateTime.now();

        List<Appointment> allAppointments = appointmentRepository.findAll();
        List<Appointment> expiredAppointments = allAppointments.stream()
                .filter(a -> LocalDateTime.of(a.getAppointmentDate(), a.getAppointmentTime()).isBefore(now)
                        && a.getStatus() == AppStatus.CONFIRMED)
                .toList();
        for (Appointment appointment : expiredAppointments) {
            appointment.setStatus(AppStatus.CANCELLED);
            appointmentRepository.save(appointment);

            // Gửi email thông báo hủy cuộc hẹn
            Customer customer = appointment.getCustomer();
            User user = customer.getUser();
            String email = user.getEmail();
            String subject = "Thông báo hủy cuộc hẹn tại VNVC";
            String body = "<h3>Xin chào " + user.getFullName() + ",</h3>"
                    + "<p>Cuộc hẹn của bạn đã bị hủy do quá hạn:</p>"
                    + "<ul>"
                    + "<li><b>Tên Trẻ:</b> " + appointment.getChild().getFullName() + "</li>"
                    + "<li><b>Ngày hẹn:</b> " + appointment.getAppointmentDate() + "</li>"
                    + "<li><b>Giờ hẹn:</b> " + appointment.getAppointmentTime() + "</li>"
                    + "</ul>"
                    + "<p>Vui lòng đặt lại lịch hẹn nếu cần.</p>"
                    + "<br><p>Trân trọng,<br>Phòng khám VNVC</p>";

            emailService.sendAppointmentNotification(email, subject, body);
        }
    }
    public boolean rescheduleAppointment(String appointmentId, RescheduleAppointmentRequest request) {
        Optional<Appointment> optionalAppointment = appointmentRepository.findByAppId(appointmentId);

        if (optionalAppointment.isPresent()) {
            Appointment appointment = optionalAppointment.get();

            if (appointment.getStatus() == AppStatus.CANCELLED || appointment.getStatus() == AppStatus.COMPLETED) {
                return false; // Không thể thay đổi cuộc hẹn đã cancel hoặc đã complete
            }
            appointment.setAppointmentDate(request.getNewDate());
            appointment.setAppointmentTime(request.getNewTime());
            appointmentRepository.save(appointment);
            return true;
        }
        return false;
    }
}