package com.swp.ChildrenVaccine.dto.response;

import com.swp.ChildrenVaccine.entities.Appointment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AppointmentSimpleDTO {
    private String appId;
    private String cusId;
    private String cusName;
    private String childName;
    private LocalDate appointmentDate;
    private String vaccineOrPackage;

    public AppointmentSimpleDTO(Appointment appointment) {
        this.appId = appointment.getAppId();
        this.cusId = appointment.getCustomer().getCusId();
        this.cusName = appointment.getCustomer().getUser().getFullName();
        this.childName = appointment.getChild().getFullName();
        this.appointmentDate = appointment.getAppointmentDate();

        if (appointment.getVaccineId() != null) {
            this.vaccineOrPackage = appointment.getVaccineId().getName();
        } else if (appointment.getPackageId() != null) {
            this.vaccineOrPackage = appointment.getPackageId().getName();
        } else {
            this.vaccineOrPackage = "N/A";
        }
    }
}
