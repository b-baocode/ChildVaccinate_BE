package com.swp.ChildrenVaccine.service;

import com.swp.ChildrenVaccine.dto.request.appointment.AppointmentRegisterRequest;
import com.swp.ChildrenVaccine.dto.response.AppointmentDTO;
import com.swp.ChildrenVaccine.dto.response.AppointmentSimpleDTO;
import com.swp.ChildrenVaccine.dto.response.TimeSlotAvailabilityDTO;
import com.swp.ChildrenVaccine.entities.Appointment;
import com.swp.ChildrenVaccine.entities.Child;
import com.swp.ChildrenVaccine.entities.Customer;
import com.swp.ChildrenVaccine.entities.Vaccine;
import com.swp.ChildrenVaccine.entities.VacinePackage;
import com.swp.ChildrenVaccine.enums.AppStatus;
import com.swp.ChildrenVaccine.enums.PaymentStatus;

import com.swp.ChildrenVaccine.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
        appointment.setCustomer(customer);

        // Lấy Child từ database
        Child child = childRepository.findById(request.getChildId())
                .orElseThrow(() -> new RuntimeException("Child not found"));
        appointment.setChild(child);

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

    public List<AppointmentSimpleDTO> getCompletedAppointmentsWithoutFeedback(String cusId) {
        return appointmentRepository.findCompletedAppointmentsWithoutFeedback(cusId)
                .stream()
                .map(AppointmentSimpleDTO::new)
                .collect(Collectors.toList());
    }

    public List<AppointmentDTO> getAppointmentsByChildId(String childId) {
        List<Appointment> appointments = appointmentRepository.findByChildId(childId);
        return appointments.stream()
                .map(AppointmentDTO::new)
                .collect(Collectors.toList());
    }

    public List<Appointment> getAppointmentsByCustomerId(String cusId) {
        return appointmentRepository.findByCustomerId(cusId);
    }

    public TimeSlotAvailabilityDTO checkTimeSlotAvailability(String date, String timeSlot) {
        LocalDate appointmentDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        String formattedTimeSlot = LocalTime.parse(timeSlot, DateTimeFormatter.ISO_TIME)
                .format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        int currentCount = appointmentRepository.countByDateAndTimeSlot(appointmentDate, formattedTimeSlot);
        System.out.println("Current count: " + currentCount);

        int maxAllowed = 5;
        boolean available = currentCount < maxAllowed;

        return new TimeSlotAvailabilityDTO(currentCount, maxAllowed, available, timeSlot, date);
    }



}