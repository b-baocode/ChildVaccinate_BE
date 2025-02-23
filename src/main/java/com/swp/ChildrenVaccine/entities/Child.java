package com.swp.ChildrenVaccine.entities;

import com.swp.ChildrenVaccine.enums.GenderEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "children")
public class Child {

    @Id
    @Column(name = "child_id", length = 50)
    private String childId;

    @JoinColumn(name = "cus_id", nullable = false, unique = true)
    private String cusId;

    @Column(name = "full_name", length = 255, nullable = false)
    private String fullName;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "gender", length = 10)
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    @Column(name = "height")
    private Float height;

    @Column(name = "weight")
    private Float weight;

    @Column(name = "blood_type", length = 5)
    private String bloodType;

    @Column(name = "allergies", columnDefinition = "TEXT")
    private String allergies;

    @Column(name = "health_note", columnDefinition = "TEXT")
    private String healthNote;


}