package com.swp.ChildrenVaccine.repository;


import com.swp.ChildrenVaccine.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    @Query(value = "SELECT TOP 1 c.cus_id AS cusId, c.user_id AS userId, c.address, c.date_of_birth AS dateOfBirth, c.gender, " +
            "u.email, u.full_name AS fullName, u.phone " +
            "FROM customers c " +
            "JOIN users u ON c.user_id = u.user_id " +
            "ORDER BY c.cus_id ASC", nativeQuery = true)
    CustomerUserProjection findFirstCustomerNative();

    Optional<Customer> findTopByOrderByCusIdDesc();
    Optional<Customer> findByUser_Email(String email);
}