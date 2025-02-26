package com.swp.ChildrenVaccine.controller;

import com.swp.ChildrenVaccine.dto.response.AppointmentDTO;
import com.swp.ChildrenVaccine.dto.response.AppointmentFeedbackDTO;
import com.swp.ChildrenVaccine.dto.response.AppointmentSimpleDTO;
import com.swp.ChildrenVaccine.entities.Appointment;
import com.swp.ChildrenVaccine.dto.request.appointment.AppointmentRegisterRequest;
import com.swp.ChildrenVaccine.enums.AppStatus;
import com.swp.ChildrenVaccine.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        try {
            List<Appointment> appointments = appointmentService.getAllAppointments();
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            logger.error("Error retrieving appointments", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/register-vaccination")
    public ResponseEntity<Map<String, Object>> createAppointment(@RequestBody AppointmentRegisterRequest request) {
        System.out.println("Received request: " + request);

        if ((request.getVaccineId() != null && request.getPackageId() != null) ||
                (request.getVaccineId() == null && request.getPackageId() == null)) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Chỉ được chọn một trong vaccineId hoặc packageId");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        try {
            Appointment createdAppointment = appointmentService.createAppointment(request);
            AppointmentDTO responseDTO = new AppointmentDTO(createdAppointment);

            Map<String, Object> successResponse = new HashMap<>();
            successResponse.put("message", "Appointment created successfully");
            successResponse.put("appointment", responseDTO);
            return ResponseEntity.ok(successResponse);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PutMapping("/update-status/{appId}")
    public ResponseEntity<Map<String, Object>> updateAppointmentStatus(
            @PathVariable String appId,
            @RequestBody Map<String, String> requestBody) {
        try {
            String statusStr = requestBody.get("status");
            if (statusStr == null) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "Status is required");
                return ResponseEntity.badRequest().body(errorResponse);
            }

            AppStatus newStatus;
            try {
                newStatus = AppStatus.valueOf(statusStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "Invalid status value");
                return ResponseEntity.badRequest().body(errorResponse);
            }

            // Lấy appointment hiện tại từ service để kiểm tra trạng thái cũ
            Appointment currentAppointment = appointmentService.getAppointmentById(appId); // Giả sử có phương thức này
            if (currentAppointment == null) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "Appointment not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }

            AppStatus currentStatus = currentAppointment.getStatus();

            // Kiểm tra nếu đang từ CANCELLED muốn chuyển sang trạng thái khác
            if (currentStatus == AppStatus.CANCELLED && newStatus != AppStatus.CANCELLED) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "Cannot change status from CANCELLED to another status");
                return ResponseEntity.badRequest().body(errorResponse);
            }

            // Kiểm tra nếu đang từ COMPLETED muốn chuyển về CONFIRMED
            if (currentStatus == AppStatus.COMPLETED && newStatus == AppStatus.CONFIRMED) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "Cannot change status from COMPLETED back to CONFIRMED");
                return ResponseEntity.badRequest().body(errorResponse);
            }

            // Nếu không vi phạm các điều kiện trên, cập nhật trạng thái
            Appointment updatedAppointment = appointmentService.updateStatus(appId, newStatus);
            Map<String, Object> successResponse = new HashMap<>();
            successResponse.put("message", "Appointment status updated successfully");
            successResponse.put("appointment", updatedAppointment);
            return ResponseEntity.ok(successResponse);
        } catch (IllegalArgumentException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (Exception e) {
            logger.error("Error updating appointment status", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Internal server error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/completed-without-feedback")
    public ResponseEntity<List<AppointmentSimpleDTO>> getCompletedAppointmentsWithoutFeedback() {
        List<AppointmentSimpleDTO> appointments = appointmentService.getCompletedAppointmentsWithoutFeedback();
        return ResponseEntity.ok(appointments);
    }
}