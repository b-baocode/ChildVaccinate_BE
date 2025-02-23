package com.swp.ChildrenVaccine.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CustomerUserDTO {
    private String cusId;
    private String userId;
    private String address;
    private LocalDate dateOfBirth;
    private String gender;
    private String email;
    private String fullName;
    private String phone;
}
