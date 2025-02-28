package com.swp.ChildrenVaccine.controller;


import com.swp.ChildrenVaccine.dto.request.RecordRequest;
import com.swp.ChildrenVaccine.dto.response.VaccinationRecordDTO;
import com.swp.ChildrenVaccine.entities.VaccinationRecord;
import com.swp.ChildrenVaccine.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/records")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @PostMapping
    public ResponseEntity<VaccinationRecord> createRecord(@RequestBody RecordRequest recordRequest) {
        VaccinationRecord savedRecord = recordService.createRecord(recordRequest);
        return ResponseEntity.ok(savedRecord);
    }

    @GetMapping("/{childId}")
    public ResponseEntity<List<VaccinationRecordDTO>> getRecordsByChildId(@PathVariable String childId) {
        List<VaccinationRecordDTO> dtos = recordService.getRecordsByChildId(childId)
                .stream()
                .map(record -> new VaccinationRecordDTO(
                        record.getId(),
                        record.getAppointment().getAppId(),
                        record.getAppointmentDate(),
                        (record.getStaff() != null && record.getStaff().getUser() != null)
                                ? record.getStaff().getUser().getFullName()
                                : null,
                        record.getSymptoms(),
                        record.getNotes()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

}