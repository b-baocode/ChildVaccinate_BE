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
    private String serviceId; // Có thể là vaccineId hoặc packageId
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String status;
    private String paymentStatus;

    public AppointmentDTO(Appointment appointment) {
        this.appId = appointment.getAppId();
        this.customerId = appointment.getCustomer().getCusId();
        this.childId = appointment.getChild().getChildId();
        this.serviceId = (appointment.getVaccineId() != null)
                ? appointment.getVaccineId().getVaccineId()
                : appointment.getPackageId().getPackageId();
        this.appointmentDate = appointment.getAppointmentDate();
        this.appointmentTime = appointment.getAppointmentTime();
        this.status = appointment.getStatus().toString();
        this.paymentStatus = appointment.getPaymentStatus().toString();
    }

    // Getters & Setters
}
