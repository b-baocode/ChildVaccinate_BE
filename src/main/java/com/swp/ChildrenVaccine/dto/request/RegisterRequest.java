package com.swp.ChildrenVaccine.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Data
public class RegisterRequest {
    private String email;
    private String fullName;
    private String password;
    private String phone;
    private String address;
    private LocalDate dateOfBirth;
    private String gender;
}
