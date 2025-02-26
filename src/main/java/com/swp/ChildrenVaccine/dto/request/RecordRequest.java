package com.swp.ChildrenVaccine.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Data
public class RecordRequest {
    private String appointmentId;
    private String staffId;
    private String symptoms;
    private String notes;
    private LocalDate appointmentDate;
}