package com.swp.ChildrenVaccine.entities;

import jakarta.persistence.*;
// import lombok.Data;

@Entity

@Table(name = "customers")
public class Customer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String email;
    private String password;

    public String getPassword() {
        return password;
    }

    public Customer(Long id, String email, String password, String fullName, String phone, String address) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
    }

    public Customer() {
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String fullName;
    
    private String phone;
    
    private String address;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
