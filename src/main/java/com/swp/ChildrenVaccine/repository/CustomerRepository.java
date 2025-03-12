package com.swp.ChildrenVaccine.repository;

import com.swp.ChildrenVaccine.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    Optional<Customer> findTopByOrderByCusIdDesc();
    Optional<Customer> findByUser_Email(String email);
    Optional<Customer> findByCusId(String cusId);
}
