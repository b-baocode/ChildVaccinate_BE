package com.swp.ChildrenVaccine.controller;


import com.swp.ChildrenVaccine.dto.request.RecordRequest;
import com.swp.ChildrenVaccine.entities.Record;
import com.swp.ChildrenVaccine.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/records")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @PostMapping
    public ResponseEntity<Record> createRecord(@RequestBody RecordRequest recordRequest) {
        Record savedRecord = recordService.createRecord(recordRequest);
        return ResponseEntity.ok(savedRecord);
    }

    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<List<Record>> getRecordsByAppointmentId(@PathVariable String appointmentId) {
        List<Record> records = recordService.getRecordsByAppointmentId(appointmentId);
        return ResponseEntity.ok(records);
    }
}