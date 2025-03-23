package com.swp.ChildrenVaccine.service;


import com.swp.ChildrenVaccine.dto.request.RecordRequest;
import com.swp.ChildrenVaccine.entities.Appointment;
import com.swp.ChildrenVaccine.entities.Staff;
import com.swp.ChildrenVaccine.entities.VaccinationRecord;
import com.swp.ChildrenVaccine.repository.AppointmentRepository;
import com.swp.ChildrenVaccine.repository.RecordRepository;
import com.swp.ChildrenVaccine.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RecordService {

    @Autowired
    private RecordRepository recordRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private StaffRepository staffRepository;


    public List<VaccinationRecord> getRecordsByChildId(String ChildId) {
        return recordRepository.findByChildId(ChildId);
    }

    public VaccinationRecord createRecord(RecordRequest recordRequest) {  Optional<Appointment> appointment = appointmentRepository.findByAppId(recordRequest.getAppointmentId());
        Optional<Staff> staff = staffRepository.findById(recordRequest.getStaffId());

        if (appointment.isPresent() && staff.isPresent()) {
            VaccinationRecord record = new VaccinationRecord();

            // Tạo recordId mới
            String lastId = recordRepository.findMaxRecordId(); // Lấy ID lớn nhất hiện có
            int newId = 1; // Mặc định là 1 nếu không có ID nào trước đó
            if (lastId != null && lastId.matches("VR\\d+")) {
                newId = Integer.parseInt(lastId.replace("VR", "")) + 1;
            }
            record.setId(String.format("VR%03d", newId)); // Format thành VR001, VR002, ...


            record.setAppointment(appointment.get());
            record.setStaff(staff.get());
            record.setSymptoms(recordRequest.getSymptoms());
            record.setNotes(recordRequest.getNotes());
            record.setAppointmentDate(recordRequest.getAppointmentDate());

            return recordRepository.save(record);
        } else {
            throw new IllegalArgumentException("Invalid Appointment or Staff ID");
        }
    }
}