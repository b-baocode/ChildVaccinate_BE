package com.swp.ChildrenVaccine.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "children")
public class Child {

    @Id
    @Column(name = "child_id", length = 50)
    private String childId;

    @Column(name = "cus_id", length = 50, nullable = false)
    private String cusId;

    @Column(name = "full_name", length = 255, nullable = false)
    private String fullName;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "gender", length = 10)
    @Enumerated(EnumType.STRING)
    private Gender gender;

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

    // Getters and Setters

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    public String getCusId() {
        return cusId;
    }

    public void setCusId(String cusId) {
        this.cusId = cusId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getHealthNote() {
        return healthNote;
    }

    public void setHealthNote(String healthNote) {
        this.healthNote = healthNote;
    }

    // Enum for gender
    public enum Gender {
        MALE, FEMALE, OTHER
    }
}