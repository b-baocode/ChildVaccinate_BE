package com.swp.ChildrenVaccine.controller;

import com.swp.ChildrenVaccine.entities.Appointment;
import com.swp.ChildrenVaccine.dto.request.appointment.AppointmentRegisterRequest;
import com.swp.ChildrenVaccine.enums.AppStatus;
import com.swp.ChildrenVaccine.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/appointment")
@CrossOrigin(origins = "http://localhost:3000") // Port của React
public class AppointmentController {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentController.class);

    @Autowired
    private AppointmentService appointmentService;

    @PostConstruct
    public void init() {
        logger.info("AppointmentAPI is initialized.");
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<Appointment>> getAllAppointments() {
        logger.info("GET /appointments/all called");
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }

    @PostMapping("/register-vaccination")
    public ResponseEntity<String> createAppointment(@RequestBody AppointmentRegisterRequest request) {
        System.out.println("Received request: " + request);

        if ((request.getVaccineId() != null && request.getPackageId() != null) ||
                (request.getVaccineId() == null && request.getPackageId() == null)) {
            return ResponseEntity.badRequest().body("Chỉ được chọn một trong vaccineId hoặc packageId");
        }

        try {
            appointmentService.createAppointment(request);
            return ResponseEntity.ok("Appointment created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{appointmentId}/status")
    public ResponseEntity<Appointment> updateAppointmentStatus(@PathVariable Long appointmentId, @RequestParam AppStatus newStatus) {
        logger.info("PUT /appointments/{}/status called with status: {}", appointmentId, newStatus);
        try {
            Appointment updatedAppointment = appointmentService.updateAppointmentStatus(appointmentId, newStatus);
            return ResponseEntity.ok(updatedAppointment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}