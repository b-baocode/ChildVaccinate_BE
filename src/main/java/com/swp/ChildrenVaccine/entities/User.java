package com.swp.ChildrenVaccine.entities;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.swp.ChildrenVaccine.enums.RoleEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Nationalized;


@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "user_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Size(max = 100)
    @NotNull
    @Column(name = "email", length = 100)
    private String email;

    @Size(max = 100)
    @Column(name = "password", length = 100)
    @NotNull
    private String password;

    @Size(max = 250)
    @Nationalized
    @Column(name = "full_name", length = 250)
    private String fullName;

    @Size(max = 20)
    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "role")
    @Enumerated(EnumType.ORDINAL)
    @NotNull
    private RoleEnum role;

    @Column(name = "active")
    @NotNull
    private boolean active;
}
