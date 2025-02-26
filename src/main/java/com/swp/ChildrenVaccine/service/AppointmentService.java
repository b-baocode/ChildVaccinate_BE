package com.swp.ChildrenVaccine.service;

import com.swp.ChildrenVaccine.dto.request.appointment.AppointmentRegisterRequest;
import com.swp.ChildrenVaccine.dto.response.AppointmentSimpleDTO;
import com.swp.ChildrenVaccine.entities.Appointment;
import com.swp.ChildrenVaccine.entities.Child;
import com.swp.ChildrenVaccine.entities.Customer;
import com.swp.ChildrenVaccine.entities.Vaccine;
import com.swp.ChildrenVaccine.entities.VacinePackage;
import com.swp.ChildrenVaccine.enums.AppStatus;
import com.swp.ChildrenVaccine.enums.PaymentStatus;

import com.swp.ChildrenVaccine.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class AppointmentService {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentService.class);

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ChildRepository childRepository;

    @Autowired
    private VaccineRepository vaccineRepository;

    @Autowired
    private VaccinePackageRepository vaccinePackageRepository;

    public Appointment createAppointment(AppointmentRegisterRequest request) {
        Appointment appointment = new Appointment();

        // Lấy Customer từ database
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        appointment.setCustomerId(customer);

        // Lấy Child từ database
        Child child = childRepository.findById(request.getChildId())
                .orElseThrow(() -> new RuntimeException("Child not found"));
        appointment.setChildId(child);

        // Lấy Vaccine từ database (nếu có)
        if (request.getVaccineId() != null) {
            Vaccine vaccine = vaccineRepository.findByVaccineId(request.getVaccineId())
                    .orElseThrow(() -> new RuntimeException("Vaccine not found"));
            appointment.setVaccineId(vaccine);
        }

        // Lấy VaccinePackage từ database (nếu có)
        if (request.getPackageId() != null) {
            VacinePackage vaccinePackage = vaccinePackageRepository.findByPackageId(request.getPackageId())
                    .orElseThrow(() -> new RuntimeException("Vaccine Package not found"));
            appointment.setPackageId(vaccinePackage);
        }

        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setAppointmentTime(request.getAppointmentTime());

        // Tạo appId mới
        String lastId = appointmentRepository.findMaxAppId();
        int newId = 1; // Mặc định là 1 nếu không có ID nào trước đó
        if (lastId != null && lastId.matches("APP\\d+")) {
            newId = Integer.parseInt(lastId.replace("APP", "")) + 1;
        }
        appointment.setAppId(String.format("APP%03d", newId));

        appointment.setStatus(AppStatus.CONFIRMED);
        appointment.setPaymentStatus(PaymentStatus.PENDING);

        return appointmentRepository.save(appointment);
    }


    public Appointment updateStatus(String appId, AppStatus newStatus) {
        Appointment appointment = appointmentRepository.findByAppId(appId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));

        AppStatus currentStatus = appointment.getStatus();

        if (currentStatus == AppStatus.CANCELLED) {
            throw new IllegalArgumentException("Cannot modify a cancelled appointment");
        }

        if (currentStatus == AppStatus.CONFIRMED &&
                (newStatus == AppStatus.COMPLETED || newStatus == AppStatus.CANCELLED)) {
            appointment.setStatus(newStatus);
        } else if (currentStatus == AppStatus.COMPLETED && newStatus == AppStatus.CANCELLED) {
            appointment.setStatus(newStatus);
        } else {
            throw new IllegalArgumentException("Invalid status transition");
        }

        return appointmentRepository.save(appointment);
    }

    //create thís for me getAppointmentById(appId)
    public Appointment getAppointmentById(String appId) {
        return appointmentRepository.findByAppId(appId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));
    }

    public List<AppointmentSimpleDTO> getCompletedAppointmentsWithoutFeedback() {
        return appointmentRepository.findCompletedAppointmentsWithoutFeedback()
                .stream()
                .map(AppointmentSimpleDTO::new)
                .collect(Collectors.toList());
    }
}