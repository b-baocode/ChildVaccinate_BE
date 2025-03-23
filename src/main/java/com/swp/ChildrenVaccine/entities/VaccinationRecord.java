package com.swp.ChildrenVaccine.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "vaccination_records")
public class Record {

    @Id
    @Column(name = "vaccination_records_id", length = 50)
    private String id;

    @ManyToOne
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)
    private Staff staff;

    @Column(name = "symptoms", columnDefinition = "TEXT")
    private String symptoms;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "appointment_date", nullable = false)
    private java.time.LocalDate appointmentDate;
}
