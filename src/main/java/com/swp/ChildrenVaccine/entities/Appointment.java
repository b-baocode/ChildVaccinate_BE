package com.swp.ChildrenVaccine.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @Column(name = "app_id", length = 50)
    private String appId;

    @OneToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customerId; // Sửa tên thành 'customer'

    @Column(name = "child_id", length = 50, nullable = false)
    private String childId;

    @Column(name = "appointment_date", nullable = false)
    private LocalDate appointmentDate;

    @Column(name = "appointment_time", nullable = false)
    private LocalTime appointmentTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 15, nullable = false)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", length = 10, nullable = false)
    private PaymentStatus paymentStatus;

    @Column(name = "vaccine_id", length = 50)
    private String vaccineId;

    @Column(name = "package_id", length = 50)
    private String packageId;

    public enum Status {
        PENDING, CONFIRMED, COMPLETED, CANCELLED
    }

    public enum PaymentStatus {
        PENDING, PAID
    }
}