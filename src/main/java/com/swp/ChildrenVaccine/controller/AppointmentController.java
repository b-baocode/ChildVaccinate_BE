package com.swp.ChildrenVaccine.controller;

import com.swp.ChildrenVaccine.dto.request.appointment.AppointmentRegisterRequest;
import com.swp.ChildrenVaccine.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;


    @PostMapping("/send-appointment-email/{appointmentId}")
    public ResponseEntity<String> sendAppointmentEmail(@PathVariable String appointmentId) {
        boolean success = appointmentService.sendAppointmentEmail(appointmentId);
        if (success) {
            return ResponseEntity.ok("Email thông báo đã được gửi thành công!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy cuộc hẹn!");
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