package com.swp.ChildrenVaccine.entities;

import com.swp.ChildrenVaccine.enums.ScheduleStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "schedules",
        uniqueConstraints = @UniqueConstraint(columnNames = {"vaccine_id", "package_id"}))
public class Schedule {

    @Id
    @Column(name = "schedule_id", length = 50)
    private String scheduleId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "child_id", nullable = false)
    private Child child;

    @ManyToOne
    @JoinColumn(name = "vaccine_id")
    private Vaccine vaccine;

    @ManyToOne
    @JoinColumn(name = "package_id")
    private VacinePackage aPackage;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "total_shot", nullable = false)
    private int totalShot;

    @Column(name = "status", length = 15, nullable = false)
    @Enumerated(EnumType.STRING)
    private ScheduleStatus status;
}
