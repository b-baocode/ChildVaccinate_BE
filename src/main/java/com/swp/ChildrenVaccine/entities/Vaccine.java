package com.swp.ChildrenVaccine.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "vaccines")
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
    private double price;

    // Getters and Setters

    public String getVaccineId() {
        return vaccineId;
    }

    public void setVaccineId(String vaccineId) {
        this.vaccineId = vaccineId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Integer getShot() {
        return shot;
    }

    public void setShot(Integer shot) {
        this.shot = shot;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}