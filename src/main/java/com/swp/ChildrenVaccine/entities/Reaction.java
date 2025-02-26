package com.swp.ChildrenVaccine.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "vaccination_reactions")
public class VaccinationReaction {

    @Id
    @Column(name = "reaction_id", length = 50)
    private String id;

    @ManyToOne
    @JoinColumn(name = "child_id", nullable = false)
    private Child child;

    @ManyToOne
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

    @Column(name = "symptoms", columnDefinition = "TEXT")
    private String symptoms;

    @Enumerated(EnumType.STRING)
    @Column(name = "severity", nullable = false)
    private Severity severity;

    @Column(name = "reaction_date", nullable = false)
    private LocalDateTime reactionDate;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum Severity {
        MILD, MODERATE, SEVERE
    }
}
