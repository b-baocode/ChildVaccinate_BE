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
    private String id;

    @Size(max = 255)
    @NotNull
    @Column(name = "email", length = 255)
    private String email;

    @Size(max = 255)
    @Column(name = "password", length = 255)
    @NotNull
    private String password;

    @Size(max = 255)
    @Nationalized
    @Column(name = "full_name", length = 255)
    private String fullName;

    @Size(max = 20)
    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @NotNull
    private RoleEnum role;

    @Column(name = "active")
    @NotNull
    private boolean active;
}
