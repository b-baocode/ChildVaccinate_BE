package com.swp.ChildrenVaccine.dto.request;

import com.swp.ChildrenVaccine.enums.Severity;

import java.time.LocalDateTime;

public class VaccinationReactionRequest {
    private LocalDateTime reactionDate;
    private String symptoms;
    private Severity severity;
    private String notes;

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
