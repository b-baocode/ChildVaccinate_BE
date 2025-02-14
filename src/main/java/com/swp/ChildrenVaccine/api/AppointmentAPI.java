package com.swp.ChildrenVaccine.api;

import com.swp.ChildrenVaccine.entities.Appointment;
import com.swp.ChildrenVaccine.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequestMapping("/appointments")
public class AppointmentAPI {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/register-vaccination")
    public ResponseEntity<String> createAppointment(
            Authentication authentication,
            @RequestParam String childId,
            @RequestParam(required = false) String vaccineId,
            @RequestParam(required = false) String packageId,
            @RequestParam LocalDate appointmentDate,
            @RequestParam LocalTime appointmentTime) {

        String customerId = authentication.getName(); // Assuming customer ID is stored as username

        try {
            appointmentService.createAppointment(customerId, childId, vaccineId, packageId, appointmentDate, appointmentTime);
            return ResponseEntity.ok("Appointment created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}