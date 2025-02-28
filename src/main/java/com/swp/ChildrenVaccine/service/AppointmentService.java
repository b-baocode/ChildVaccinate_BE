package com.swp.ChildrenVaccine.service;

import com.swp.ChildrenVaccine.entities.Appointment;
import com.swp.ChildrenVaccine.entities.Customer;
import com.swp.ChildrenVaccine.repository.AppointmentRepository;
import com.swp.ChildrenVaccine.repository.ChildRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    //Get customer information using session after login

    //Get all child list of the customer

    //Get all vaccine list

    //Get all package list

//    public void createAppointment(String customerId, String childId, String vaccineId, String packageId, LocalDate appointmentDate, LocalTime appointmentTime) throws Exception {
//
//        // Validate that either a vaccine or a package is selected, but not both
//        if ((vaccineId == null && packageId == null) || (vaccineId != null && packageId != null)) {
//            throw new Exception("Either vaccine or package must be selected, but not both");
//        }
//
//        // Create and save the appointment
//        Appointment appointment = new Appointment();
//        appointment.getCustomerId();
//        appointment.setChildId(childId);
//        appointment.setVaccineId(vaccineId);
//        appointment.setPackageId(packageId);
//        appointment.setAppointmentDate(appointmentDate);
//        appointment.setAppointmentTime(appointmentTime);
//        appointment.setStatus(Appointment.Status.CONFIRMED);
//
//        appointmentRepository.save(appointment);
//    }

    public List<Appointment> findAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointment findByAppId(String appId) {
        return appointmentRepository.findByAppId(appId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy cuộc hẹn với ID: " + appId));
    }


}