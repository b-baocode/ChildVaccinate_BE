package com.swp.ChildrenVaccine.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class StaffRequest {

//    private String userId;
    private String department;
    private LocalDate hireDate;
    private String qualification;
    private String specialization;
}
