package com.swp.ChildrenVaccine.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "vaccination_records")
public class VaccinationRecord {

    @Id
    @Column(name = "vaccination_records_id", length = 50)
    private String vaccinationRecordsId;

    @Column(name = "appointment_id", length = 50, nullable = false)
    private String appointmentId;

    @Column(name = "staff_id", length = 50, nullable = false)
    private String staffId;

    @Column(name = "symptoms", columnDefinition = "TEXT")
    private String symptoms;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "appointment_date", nullable = false)
    private LocalDate appointmentDate;

    // Getters and Setters
}
