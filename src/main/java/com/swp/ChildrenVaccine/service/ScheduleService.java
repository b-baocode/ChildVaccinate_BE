package com.swp.ChildrenVaccine.service;

import com.swp.ChildrenVaccine.dto.request.ScheduleRequest;
import com.swp.ChildrenVaccine.dto.response.OverdueScheduleDTO;
import com.swp.ChildrenVaccine.dto.response.ScheduleDTO;
import com.swp.ChildrenVaccine.entities.*;
import com.swp.ChildrenVaccine.enums.AppStatus;
import com.swp.ChildrenVaccine.enums.ScheduleStatus;
import com.swp.ChildrenVaccine.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private VaccineRepository vaccineRepository;

    @Autowired
    private VaccinePackageRepository packageRepository;

    @Autowired
    private VaccinationRelationRepository vaccinationRelationRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ChildRepository childRepository;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private AppointmentRepository appointmentRepository;

    private List<OverdueScheduleDTO> overdueSchedules = new ArrayList<>();

    public Schedule createSchedule(ScheduleRequest scheduleRequest) {
        Schedule schedule = new Schedule();
        String lastId = scheduleRepository.findMaxAppId();
        int newId = 1; // Mặc định là 1 nếu không có ID nào trước đó
        if (lastId != null && lastId.matches("SCHE\\d+")) {
            newId = Integer.parseInt(lastId.replace("SCHE", "")) + 1;
        }
        schedule.setScheduleId(String.format("SCHE%03d", newId));

        //customer lấy từ database
        schedule.setCustomer(customerRepository.findById(scheduleRequest.getCustomerId()).orElseThrow());

        Child child = childRepository.findById(scheduleRequest.getChildId()).orElseThrow();
        schedule.setChild(childRepository.findById(scheduleRequest.getChildId()).orElseThrow());
        if (hasActiveSchedule(child)) {
            throw new IllegalStateException("Child already has an active schedule.");
        }

        if (scheduleRequest.getVaccineId() != null) {
            Vaccine vaccine = vaccineRepository.findById(scheduleRequest.getVaccineId()).orElseThrow();
            schedule.setVaccine(vaccine);
            schedule.setTotalShot(vaccine.getShotNumber());
        } else if (scheduleRequest.getPackageId() != null) {
            VacinePackage aPackage = packageRepository.findById(scheduleRequest.getPackageId()).orElseThrow();
            schedule.setAPackage(aPackage);
            int totalShots = vaccinationRelationRepository.findByPackageId(aPackage.getPackageId())
                    .stream()
                    .mapToInt(relation -> relation.getVaccine().getShotNumber())
                    .sum();
            schedule.setTotalShot(totalShots);
        }

        schedule.setStartDate(scheduleRequest.getStartDate());
        schedule.setStatus(ScheduleStatus.ACTIVE);
        Schedule savedSchedule = scheduleRepository.save(schedule);

        appointmentService.createAppointmentsForSchedule(savedSchedule, scheduleRequest);
        return scheduleRepository.save(schedule);
    }

    private boolean hasActiveSchedule(Child child) {
        return !scheduleRepository.findByChildAndStatus(child, ScheduleStatus.ACTIVE).isEmpty();
    }

    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleRepository.findAll();
        return schedules.stream().map(ScheduleDTO::new).collect(Collectors.toList());
    }

    public List<ScheduleDTO> getSchedulesByCustomerId(String cusId) {
        List<Schedule> schedules = scheduleRepository.findByCustomer(cusId);
        return schedules.stream().map(ScheduleDTO::new).collect(Collectors.toList());
    }

    public Schedule updateScheduleStatus(String scheduleId, ScheduleStatus newStatus) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new IllegalArgumentException("Schedule not found"));
        schedule.setStatus(newStatus);
        return scheduleRepository.save(schedule);
    }

    public List<ScheduleDTO> getSchedulesByChildId(String childId) {
        Child child = childRepository.findById(childId).orElseThrow(() -> new IllegalArgumentException("Child not found"));
        List<Schedule> schedules = scheduleRepository.findByChild(child);
        return schedules.stream().map(ScheduleDTO::new).collect(Collectors.toList());
    }

    public List<ScheduleDTO> getSchedulesByPhoneNumber(String phoneNumber) {
        List<Schedule> schedules = scheduleRepository.findByPhoneNumber(phoneNumber);
        return schedules.stream()
                .map(ScheduleDTO::new)
                .collect(Collectors.toList());
    }

    public void updateScheduleStatusIfAllAppointmentsCompleted(String scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found"));

        List<Appointment> appointments = appointmentRepository.findBySchedule(schedule);
        boolean allCompleted = appointments.stream()
                .allMatch(appointment -> appointment.getStatus() == AppStatus.COMPLETED);
        boolean anyCancelled = appointments.stream()
                .anyMatch(appointment -> appointment.getStatus() == AppStatus.CANCELLED);

        if (anyCancelled) {
            appointments.stream()
                    .filter(appointment -> appointment.getStatus() != AppStatus.COMPLETED)
                    .forEach(appointment -> {
                        appointment.setStatus(AppStatus.CANCELLED);
                        appointmentRepository.save(appointment);
                    });
            schedule.setStatus(ScheduleStatus.CANCELLED);
        } else if (allCompleted) {
            schedule.setStatus(ScheduleStatus.COMPLETED);
        }

        scheduleRepository.save(schedule);
    }


    public List<Schedule> getSchedulesWithOverdueAppointments() {
        LocalDate today = LocalDate.now();
        List<Schedule> schedules = scheduleRepository.findAll();
        return schedules.stream()
                .filter(schedule -> appointmentRepository.findBySchedule(schedule).stream()
                        .anyMatch(appointment -> appointment.getAppointmentDate().isBefore(today) && appointment.getStatus() == AppStatus.CONFIRMED))
                .collect(Collectors.toList());
    }

    public List<OverdueScheduleDTO> getOverdueSchedulesWithAppointments() {
        LocalDate today = LocalDate.now();
        List<Schedule> schedules = scheduleRepository.findAll();
        List<OverdueScheduleDTO> overdueSchedules = new ArrayList<>();

        for (Schedule schedule : schedules) {
            List<Appointment> overdueAppointments = appointmentRepository.findBySchedule(schedule).stream()
                    .filter(appointment -> appointment.getAppointmentDate().isBefore(today) && appointment.getStatus() == AppStatus.CONFIRMED)
                    .collect(Collectors.toList());

            for (Appointment overdueAppointment : overdueAppointments) {
                overdueSchedules.add(new OverdueScheduleDTO(schedule, overdueAppointment));
            }
        }

        return overdueSchedules;
    }

    public void checkOverdueSchedules() {
        LocalDate today = LocalDate.now();
        List<Schedule> schedules = scheduleRepository.findAll();
        overdueSchedules.clear();

        for (Schedule schedule : schedules) {
            List<Appointment> overdueAppointments = appointmentRepository.findBySchedule(schedule).stream()
                    .filter(appointment -> appointment.getAppointmentDate().isBefore(today) && appointment.getStatus() == AppStatus.CONFIRMED)
                    .collect(Collectors.toList());

            for (Appointment overdueAppointment : overdueAppointments) {
                overdueSchedules.add(new OverdueScheduleDTO(schedule, overdueAppointment));
            }
        }
    }

    public List<OverdueScheduleDTO> getOverdueSchedules() {
        return overdueSchedules;
    }

}