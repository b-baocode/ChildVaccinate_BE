package com.swp.ChildrenVaccine.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "staff")
public class Staff {

    @Id
    @Column(name = "staff_id", length = 50)
    private String id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "department", length = 255)
    private String department;

    @Column(name = "hire_date")
    private LocalDate hireDate;

    @Column(name = "qualification", columnDefinition = "TEXT")
    private String qualification;

    @Column(name = "specialization", length = 255)
    private String specialization;
}
