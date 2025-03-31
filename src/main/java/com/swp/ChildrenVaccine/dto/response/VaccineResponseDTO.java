package com.swp.ChildrenVaccine.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VaccineResponseDTO {
    private String vaccineName;
    private int count;
    private double price;
}
