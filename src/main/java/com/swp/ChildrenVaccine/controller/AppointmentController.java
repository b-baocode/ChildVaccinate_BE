package com.swp.ChildrenVaccine.controller;

import com.swp.ChildrenVaccine.dto.request.RescheduleAppointmentRequest;
import com.swp.ChildrenVaccine.dto.request.appointment.AppointmentRegisterRequest;
import com.swp.ChildrenVaccine.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;


    @PostMapping("/send-reminder-emails/{cusId}")
    public ResponseEntity<String> sendReminderEmailsForUpcomingAppointments(@PathVariable String cusId) {
        try {
            appointmentService.sendReminderEmailsForUpcomingAppointments(cusId);
            return ResponseEntity.ok("Reminder emails sent successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send reminder emails.");
        }
    }

    @PutMapping("/reschedule-appointment/{appointmentId}")
    public ResponseEntity<String> rescheduleAppointment(
            @PathVariable String appointmentId,
            @Valid @RequestBody RescheduleAppointmentRequest request) {

        boolean updated = appointmentService.rescheduleAppointment(appointmentId, request);

        if (updated) {
            return ResponseEntity.ok("Lịch hẹn đã được cập nhật thành công!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Không thể cập nhật lịch hẹn. Vui lòng kiểm tra thông tin!");
        }
    }


//    @PostMapping("/register-vaccination")
//    public ResponseEntity<String> createAppointment(@RequestBody AppointmentRegisterRequest request) {
//        try {
//            appointmentService.createAppointment(
//                    request.getCustomerId(),
//                    request.getChildId(),
//                    request.getVaccineId(),
//                    request.getPackageId(),
//                    request.getAppointmentDate(),
//                    request.getAppointmentTime()
//            );
//            return ResponseEntity.ok("Appointment created successfully");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//    }
}