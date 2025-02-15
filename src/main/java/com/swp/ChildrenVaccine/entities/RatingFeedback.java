package com.swp.ChildrenVaccine.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "RatingFeedback")
public class RatingFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "appointment_id", length = 50, nullable = false)
    private String appointmentId;

    @Column(name = "customer_id", length = 50, nullable = false)
    private String customerId;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "feedback", columnDefinition = "TEXT")
    private String feedback;

    // Getters and Setters
}
