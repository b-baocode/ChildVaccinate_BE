package com.swp.ChildrenVaccine.entities;

import com.swp.ChildrenVaccine.enums.AppStatus;
import com.swp.ChildrenVaccine.enums.PaymentStatus;
import com.swp.ChildrenVaccine.repository.AppointmentRepository;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalTime;

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
            int newId = (lastId != null) ? Integer.parseInt(lastId.substring(1)) + 1 : 1;
            this.appId = String.format("A%03d", newId);
        }
    }

    // Getters and Setters

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public AppStatus getStatus() {
        return status;
    }

    public void setStatus(AppStatus status) {
        this.status = status;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getVaccineId() {
        return vaccineId;
    }

    public void setVaccineId(String vaccineId) {
        this.vaccineId = vaccineId;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }
}