package com.swp.ChildrenVaccine.api;

import com.swp.ChildrenVaccine.entities.Appointment;
import com.swp.ChildrenVaccine.dto.request.appointment.AppointmentRegisterRequest;
import com.swp.ChildrenVaccine.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequestMapping("/appointments")
public class AppointmentAPI {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/register-vaccination")
    public ResponseEntity<String> createAppointment(@RequestBody AppointmentRegisterRequest request) {
        try {
            appointmentService.createAppointment(
                    request.getCustomerId(),
                    request.getChildId(),
                    request.getVaccineId(),
                    request.getPackageId(),
                    request.getAppointmentDate(),
                    request.getAppointmentTime()
            );
            return ResponseEntity.ok("Appointment created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}