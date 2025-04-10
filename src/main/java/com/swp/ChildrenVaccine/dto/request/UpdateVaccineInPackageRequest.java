package com.swp.ChildrenVaccine.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public class UpdateVaccineInPackageRequest {
    private List<String> vaccineIds;
    private String name;
    private String description;

    public void setVaccineIds(List<String> vaccineIds) {
        this.vaccineIds = vaccineIds;
        this.name = name;
        this.description = description;
    }
}