package com.swp.ChildrenVaccine.service;

import com.swp.ChildrenVaccine.dto.request.appointment.AppointmentRegisterRequest;
import com.swp.ChildrenVaccine.entities.Appointment;
import com.swp.ChildrenVaccine.enums.AppStatus;
import com.swp.ChildrenVaccine.enums.PaymentStatus;
import com.swp.ChildrenVaccine.mapper.AppointmentMapper;
import com.swp.ChildrenVaccine.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@Service
public class AppointmentService {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentService.class);

    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointment createAppointment(AppointmentRegisterRequest request) {
        Appointment appointment = AppointmentMapper.INSTANCE.toEntity(request);

        // Tạo appId mới
        String lastId = appointmentRepository.findMaxAppId();
        int newId = (lastId != null) ? Integer.parseInt(lastId.replaceAll("[^0-9]", "")) + 1 : 1;
        appointment.setAppId(String.format("APP%03d", newId));

        appointment.setStatus(AppStatus.CONFIRMED);
        appointment.setPaymentStatus(PaymentStatus.PENDING);

        return appointmentRepository.save(appointment);
    }

    public Appointment updateAppointmentStatus(Long appointmentId, AppStatus newStatus) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));

        appointment.setStatus(newStatus);
        return appointmentRepository.save(appointment);
    }
}