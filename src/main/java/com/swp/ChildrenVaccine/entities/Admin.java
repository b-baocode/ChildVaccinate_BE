package com.swp.ChildrenVaccine.entities;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "admins")
public class Admin {
    @Id
    @Column(name = "admin_id", length = 50)
    private String adminId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "department", length = 255)
    private String department;
}
