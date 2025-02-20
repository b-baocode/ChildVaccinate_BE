package com.swp.ChildrenVaccine.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "RatingFeedback")
public class RatingFeedback {

    @Id
    @Column(name = "Feedback_id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "rating", nullable = false)
    private int rating;

    @Column(name = "feedback")
    private String feedback;

    // Getters and Setters
}
