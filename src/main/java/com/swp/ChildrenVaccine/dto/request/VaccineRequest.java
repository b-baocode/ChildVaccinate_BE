package com.swp.ChildrenVaccine.dto.request;

import java.math.BigDecimal;

public class VaccineRequest {
    private String name;
    private String manufacturer;
    private BigDecimal price;
    private int quantity;
    private int shotNumber;
    private int gapDays;
    private String description;
    private int ageMonth;

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getShotNumber() {
        return shotNumber;
    }

    public void setShotNumber(int shotNumber) {
        this.shotNumber = shotNumber;
    }

    public int getGapDays() {
        return gapDays;
    }

    public void setGapDays(int gapDays) {
        this.gapDays = gapDays;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAgeMonth() {
        return ageMonth;
    }

    public void setAgeMonth(int ageMonth) {
        this.ageMonth = ageMonth;
    }
}