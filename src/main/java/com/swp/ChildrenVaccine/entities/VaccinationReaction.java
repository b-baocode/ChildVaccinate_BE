package com.swp.ChildrenVaccine.entities;

import com.swp.ChildrenVaccine.enums.Severity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.List;

//@Data
@Entity
public class VaccinationReaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private LocalDateTime reactionDate; // Ngày và giờ xảy ra phản ứng
    private String symptoms; // Các triệu chứng phản ứng
    private Severity severity; // Mức độ phản ứng (nhẹ, nặng, khẩn cấp)
    private String notes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getReactionDate() {
        return reactionDate;
    }

    public void setReactionDate(LocalDateTime reactionDate) {
        this.reactionDate = reactionDate;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
