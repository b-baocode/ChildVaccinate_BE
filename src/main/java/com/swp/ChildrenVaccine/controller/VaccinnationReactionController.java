package com.swp.ChildrenVaccine.controller;

import com.swp.ChildrenVaccine.entities.Customer;
import com.swp.ChildrenVaccine.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vaccination-reactions")
public class VaccinnationReactionController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public List<Customer> getAllReactions() {
        return customerRepository.findAll();
    }

    @PostMapping
    public Customer createReaction(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Customer> updateReaction(@PathVariable Long id, @RequestBody Customer customerDetails) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            customer.setEmail(customerDetails.getEmail());
            customer.setFullName(customerDetails.getFullName());
            customer.setPhone(customerDetails.getPhone());
            customer.setAddress(customerDetails.getAddress());
            final Customer updatedCustomer = customerRepository.save(customer);
            return ResponseEntity.ok(updatedCustomer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}