package com.swp.ChildrenVaccine.dto.request.appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppointmentRegisterRequest {
    private String customerId;
    private String childId;
    private String vaccineId;
    private String packageId;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;

}
