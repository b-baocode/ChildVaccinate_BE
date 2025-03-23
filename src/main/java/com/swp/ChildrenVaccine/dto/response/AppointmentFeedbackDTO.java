package com.swp.ChildrenVaccine.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentFeedbackDTO {
    private String appointmentId;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String childName;
    private String vaccineOrPackageName;
}

