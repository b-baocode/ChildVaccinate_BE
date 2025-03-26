package com.swp.ChildrenVaccine.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Vaccination_Relation")
public class VaccinationRelation {

    @Id
    @Column(name = "relation_id", length = 50)
    private String relationId;

    @ManyToOne
    @JoinColumn(name = "package_id", nullable = false)
    private VacinePackage aPackage;

    @ManyToOne
    @JoinColumn(name = "vaccine_id", nullable = false)
    private Vaccine vaccine;
}