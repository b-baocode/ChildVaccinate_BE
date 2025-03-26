package com.swp.ChildrenVaccine.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "Vaccines")
public class Vaccine {

    @Id
    @Column(name = "vaccine_id", length = 50)
    private String vaccineId;

    @Column(name = "name", columnDefinition = "NVARCHAR(255)", nullable = false)
    private String name;

    @Column(name = "gap_days", nullable = false)
    private int gapDays;

    @Column(name = "description", columnDefinition = "NVARCHAR(500)")
    private String description;

    @Column(name = "manufacturer", columnDefinition = "NVARCHAR(255)")
    private String manufacturer;

    @Column(name = "shot_number", nullable = false)
    private int shotNumber;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
}