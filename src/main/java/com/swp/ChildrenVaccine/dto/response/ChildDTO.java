package com.swp.ChildrenVaccine.dto.response;

import com.swp.ChildrenVaccine.entities.Child;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import com.swp.ChildrenVaccine.enums.Gender;

@Getter
@Setter
public class ChildDTO {
    private String childId;
    private String customerId; // Chỉ lấy ID của Customer
    private String fullName;
    private LocalDate dateOfBirth;
    private Gender gender;
    private Float height;
    private Float weight;
    private String bloodType;
    private String allergies;
    private String healthNote;

    public ChildDTO(Child child) {
        this.childId = child.getChildId();
        this.customerId = child.getCusId().getCusId(); // Lấy ID từ Customer entity
        this.fullName = child.getFullName();
        this.dateOfBirth = child.getDateOfBirth();
        this.gender = child.getGender();
        this.height = child.getHeight();
        this.weight = child.getWeight();
        this.bloodType = child.getBloodType();
        this.allergies = child.getAllergies();
        this.healthNote = child.getHealthNote();
    }
}
