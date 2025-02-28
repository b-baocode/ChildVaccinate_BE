package com.swp.ChildrenVaccine.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "vaccines")
@Getter
@Setter
public class Vaccine {

    @Id
    @Column(name = "vaccine_id", length = 50)
    private String vaccineId;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "manufacturer", length = 255)
    private String manufacturer;

    @Column(name = "shot")
    private Integer shot;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;


}