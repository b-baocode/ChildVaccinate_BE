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
    private String customerName;
    private String childId;
    private String childName;
    private String scheduleId;
    private String serviceName;
    private int shotNumber;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String status;
    private String paymentStatus;
    private String mailNotice;
    private String phoneNumber;

    public AppointmentDTO(Appointment appointment) {
        this.appId = appointment.getAppId();
        this.customerId = appointment.getCustomer().getCusId();
        this.customerName = appointment.getCustomer().getUser().getFullName();
        this.childId = appointment.getChild().getChildId();
        this.childName = appointment.getChild().getFullName();
        this.scheduleId = appointment.getSchedule().getScheduleId();
        this.serviceName = appointment.getVaccine().getName();
        this.shotNumber = appointment.getShotNumber();
        this.appointmentDate = appointment.getAppointmentDate();
        this.appointmentTime = appointment.getAppointmentTime();
        this.status = appointment.getStatus().toString();
        this.paymentStatus = appointment.getPaymentStatus().toString();
        this.mailNotice = appointment.getMailNotice().toString();
        this.phoneNumber = appointment.getCustomer().getUser().getPhone();
    }

}
