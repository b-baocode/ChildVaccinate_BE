package com.swp.ChildrenVaccine.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@Data
public class CreateStaffRequest {
    private String email;
    private String fullName;
    private String password;
    private String phone;
    private String department;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate hireDate;
    private String qualification;
    private String specialization;
}
