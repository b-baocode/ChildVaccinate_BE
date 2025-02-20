package com.swp.ChildrenVaccine.entities;

import com.swp.ChildrenVaccine.enums.AppStatus;
import com.swp.ChildrenVaccine.enums.PaymentStatus;
import com.swp.ChildrenVaccine.repository.AppointmentRepository;
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

    @Column(name = "customer_id", length = 50, nullable = false)
    private String customerId;

    @Column(name = "child_id", length = 50, nullable = false)
    private String childId;

    @Column(name = "appointment_date", nullable = false)
    private LocalDate appointmentDate;

    @Column(name = "appointment_time", nullable = false)
    private LocalTime appointmentTime;

    @Column(name = "status", length = 15, nullable = false)
    @Enumerated(EnumType.STRING)
    private AppStatus status;

    @Column(name = "payment_status", length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus  paymentStatus;

    @Column(name = "vaccine_id", length = 50)
    private String vaccineId;

    @Column(name = "package_id", length = 50)
    private String packageId;

    @Transient
    private AppointmentRepository appointmentRepository;

    // Tự động tạo appId theo dạng A001, A002...
    @PrePersist
    private void generateAppId() {
        if (this.appId == null || this.appId.isEmpty()) {
            String lastId = appointmentRepository.findMaxAppId();
            int newId = 1;
            if (lastId != null && lastId.startsWith("APP")) {
                try {
                    newId = Integer.parseInt(lastId.substring(1)) + 1;
                } catch (NumberFormatException e) {
                    // Handle the case where the lastId is not in the expected format
                    newId = 1;
                }
            }
            this.appId = String.format("APP%03d", newId);
        }
    }
}