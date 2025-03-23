package com.swp.ChildrenVaccine.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class VaccinationRecordDTO {
    private String id;
    private String appointmentId;
    private LocalDate appointmentDate;
    private String staffName;
    private String symptoms;
    private String notes;
}

