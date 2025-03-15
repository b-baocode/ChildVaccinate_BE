package com.swp.ChildrenVaccine.controller;

import com.swp.ChildrenVaccine.entities.Vaccine;
import com.swp.ChildrenVaccine.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/staff/count")
    public ResponseEntity<Long> getNumberOfStaff() {
        long numberOfStaff = adminService.getNumberOfStaff();
        return ResponseEntity.ok(numberOfStaff);
    }

    @GetMapping("/appointments/today/count")
    public ResponseEntity<Long> getNumberOfAppointmentsForToday() {
        long numberOfAppointments = adminService.getNumberOfAppointmentsForToday();

        return ResponseEntity.ok(numberOfAppointments);
    }

    @GetMapping("/revenue")
    public ResponseEntity<String> getTotalRevenue() {
        return ResponseEntity.ok((adminService.getTotalRevenue()));
    }

    @GetMapping("/top-vaccines")
    public ResponseEntity<List<Vaccine>> getTop5Vaccines() {
        List<Vaccine> topVaccines = adminService.getTop5Vaccines();
        return ResponseEntity.ok(topVaccines);
    }

}
