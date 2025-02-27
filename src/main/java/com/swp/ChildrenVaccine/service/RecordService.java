package com.swp.ChildrenVaccine.service;


import com.swp.ChildrenVaccine.dto.request.RecordRequest;
import com.swp.ChildrenVaccine.entities.Appointment;
import com.swp.ChildrenVaccine.entities.Record;
import com.swp.ChildrenVaccine.entities.Staff;
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

    public List<Record> getRecordsByAppointmentId(String appointmentId) {
        return recordRepository.findByAppointmentId(appointmentId);
    }

    public Record createRecord(RecordRequest recordRequest) {  Optional<Appointment> appointment = appointmentRepository.findById(recordRequest.getAppointmentId());
        Optional<Staff> staff = staffRepository.findById(recordRequest.getStaffId());

        if (appointment.isPresent() && staff.isPresent()) {
            Record record = new Record();

            // Tạo ID cho record sử dụng timestamp để đảm bảo unique
            String recordId = "VR" + System.currentTimeMillis();
            record.setId(recordId); // Sử dụng setter đúng tên

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