package com.swp.ChildrenVaccine.controller;

import com.swp.ChildrenVaccine.entities.Appointment;
import com.swp.ChildrenVaccine.entities.Child;
import com.swp.ChildrenVaccine.service.AppointmentService;
import com.swp.ChildrenVaccine.service.ChildService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
@RequiredArgsConstructor
public class StaffController {

    private final AppointmentService appointmentService;
    private final ChildService childrenService;

    //  API: Lấy tất cả cuộc hẹn
    @GetMapping("/getAllAppointments")
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> appointments = appointmentService.findAllAppointments();
        return ResponseEntity.ok(appointments);
    }

    //  API: Lấy cuộc hẹn theo ID
    @GetMapping("/getAppointmentById/{appId}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable String appId) {
        Appointment appointment = appointmentService.findByAppId(appId);
        return ResponseEntity.ok(appointment);
    }

    @GetMapping("/getAllChildren")
    public ResponseEntity<List<Child>> getAllChildren() {
        return ResponseEntity.ok(childrenService.getAllChildren());
    }

    @GetMapping("/getChildrenById/{childId}")
    public ResponseEntity<Child> getChildById(@PathVariable String childId) {
        return ResponseEntity.ok(childrenService.getChildById(childId));
    }

}
