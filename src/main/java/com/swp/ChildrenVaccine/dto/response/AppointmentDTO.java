package com.swp.ChildrenVaccine.dto.response;

import com.swp.ChildrenVaccine.entities.Appointment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class AppointmentDTO {
    private String appId;
    private String customerId;
    private String childId;
    private String scheduleId;
    private String serviceName;
    private int shotNumber;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String status;
    private String paymentStatus;
    private String mailNotice;

    public AppointmentDTO(Appointment appointment) {
        this.appId = appointment.getAppId();
        this.customerId = appointment.getCustomer().getCusId();
        this.childId = appointment.getChild().getChildId();
        this.scheduleId = appointment.getSchedule().getScheduleId();
        this.serviceName = appointment.getVaccine().getName();
        this.shotNumber = appointment.getShotNumber();
        this.appointmentDate = appointment.getAppointmentDate();
        this.appointmentTime = appointment.getAppointmentTime();
        this.status = appointment.getStatus().toString();
        this.paymentStatus = appointment.getPaymentStatus().toString();
        this.mailNotice = appointment.getMailNotice().toString();
    }

    // Getters & Setters
}
