package com.swp.ChildrenVaccine.entities;

import com.swp.ChildrenVaccine.enums.AppStatus;
import com.swp.ChildrenVaccine.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @Column(name = "app_id", length = 50)
    private String appId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customerId;

    @OneToOne
    @JoinColumn(name = "child_id")
    private Child childId;

    @Column(name = "appointment_date", nullable = false)
    private LocalDate appointmentDate;

    @Column(name = "appointment_time", nullable = false)
    private LocalTime appointmentTime;

    @Column(name = "status", length = 15, nullable = false)
    @Enumerated(EnumType.STRING)
    private AppStatus status;

    @Column(name = "payment_status", length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @OneToOne
    @JoinColumn(name = "vaccine_id")
    private Vaccine vaccineId;

    @OneToOne
    @JoinColumn(name = "package_id")
    private VacinePackage packageId;

    @Column(name = "is_email_sent", nullable = false, columnDefinition = "BIT DEFAULT 0")
    private boolean isEmailSent = false;

}