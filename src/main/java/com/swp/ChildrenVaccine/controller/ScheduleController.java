package com.swp.ChildrenVaccine.controller;

import com.swp.ChildrenVaccine.dto.request.ScheduleRequest;
import com.swp.ChildrenVaccine.dto.response.ScheduleDTO;
import com.swp.ChildrenVaccine.entities.Schedule;
import com.swp.ChildrenVaccine.enums.ScheduleStatus;
import com.swp.ChildrenVaccine.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<Schedule> createSchedule(@RequestBody ScheduleRequest scheduleRequest) {
        Schedule schedule = scheduleService.createSchedule(scheduleRequest);
        return ResponseEntity.ok(schedule);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleDTO>> getAllSchedules() {
        List<ScheduleDTO> schedules = scheduleService.getAllSchedules();
        return ResponseEntity.ok(schedules);
    }
    @GetMapping("/{cusId}")
    public ResponseEntity<List<ScheduleDTO>> getSchedulesByCustomerId(@PathVariable String cusId) {
        List<ScheduleDTO> schedules = scheduleService.getSchedulesByCustomerId(cusId);
        return ResponseEntity.ok(schedules);
    }

    @PutMapping("/update-status/{scheduleId}")
    public ResponseEntity<Map<String, Object>> updateScheduleStatus(
            @PathVariable String scheduleId,
            @RequestBody Map<String, String> requestBody) {
        try {
            String statusStr = requestBody.get("status");
            if (statusStr == null) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "Status is required");
                return ResponseEntity.badRequest().body(errorResponse);
            }

            ScheduleStatus newStatus;
            try {
                newStatus = ScheduleStatus.valueOf(statusStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "Invalid status value");
                return ResponseEntity.badRequest().body(errorResponse);
            }

            Schedule updatedSchedule = scheduleService.updateScheduleStatus(scheduleId, newStatus);
            Map<String, Object> successResponse = new HashMap<>();
            successResponse.put("message", "Schedule status updated successfully");
            successResponse.put("schedule", updatedSchedule);
            return ResponseEntity.ok(successResponse);
        } catch (IllegalArgumentException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Internal server error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/by-childId/{childId}")
    public ResponseEntity<List<ScheduleDTO>> getSchedulesByChildId(@PathVariable String childId) {
        try {
            List<ScheduleDTO> schedules = scheduleService.getSchedulesByChildId(childId);
            if (schedules.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(schedules);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/update-status-if-completed/{scheduleId}")
    public ResponseEntity<Void> updateScheduleStatusIfAllAppointmentsCompleted(@PathVariable String scheduleId) {
        try {
            scheduleService.updateScheduleStatusIfAllAppointmentsCompleted(scheduleId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/by-phone/{phoneNumber}")
    public ResponseEntity<List<ScheduleDTO>> getSchedulesByPhoneNumber(@PathVariable String phoneNumber) {
        List<ScheduleDTO> schedules = scheduleService.getSchedulesByPhoneNumber(phoneNumber);
        return ResponseEntity.ok(schedules);
    }
}