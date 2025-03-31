package com.swp.ChildrenVaccine.controller;

import com.swp.ChildrenVaccine.dto.request.CreateStaffRequest;
import com.swp.ChildrenVaccine.dto.request.RegisterRequest;
import com.swp.ChildrenVaccine.entities.Staff;
import com.swp.ChildrenVaccine.entities.Vaccine;
import com.swp.ChildrenVaccine.exception.EmailAlreadyExistsException;
import com.swp.ChildrenVaccine.service.AdminService;
import com.swp.ChildrenVaccine.service.AuthenticationService;
import com.swp.ChildrenVaccine.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private StaffService staffService;
    @Autowired
    private AuthenticationService authenticationService;

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
    public ResponseEntity<List<?>> getTop5Vaccines() {
        List<?> topVaccines = adminService.getTop5Vaccines();
        return ResponseEntity.ok(topVaccines);
    }

    @GetMapping("/staffs")
    public ResponseEntity<List<Staff>> getAllStaffs() {
        List<Staff> staffs = staffService.getAllStaffs();
        return ResponseEntity.ok(staffs);
    }

    @PostMapping("/staff/register")
    public ResponseEntity<?> registerStaff(@RequestBody CreateStaffRequest request) {
        try {
            adminService.createStaff(request);
            return ResponseEntity.ok("Đăng ký thành công!");
        } catch (EmailAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/staff/update/{id}")
    public ResponseEntity<?> updateStaff(@PathVariable String id,@RequestBody CreateStaffRequest request) {
        try {
            adminService.updateStaff(id, request);
            return ResponseEntity.ok("Cập nhật thành công!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/staff/delete/{id}")
    public ResponseEntity<?> deleteStaff(@PathVariable String id) {
        try {
            adminService.deleteStaff(id);
            return ResponseEntity.ok("Xóa thành công!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
