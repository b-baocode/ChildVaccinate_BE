package com.swp.ChildrenVaccine.controller;

import com.swp.ChildrenVaccine.dto.request.CustomerUserDTO;
import com.swp.ChildrenVaccine.dto.request.UpdateCustomerRequest;
import com.swp.ChildrenVaccine.entities.Customer;
import com.swp.ChildrenVaccine.repository.CustomerRepository;
import com.swp.ChildrenVaccine.repository.CustomerUserProjection;
import com.swp.ChildrenVaccine.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerService customerService;

    @GetMapping("/{userId}/profile")
    public ResponseEntity<CustomerUserProjection> getCustomerProfile(@PathVariable String userId) {
        CustomerUserProjection customerUserDTO = customerRepository.findCustomerProfile(userId);
        if (customerUserDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customerUserDTO);
    }


    @PutMapping("/update/{cusId}")
    public ResponseEntity<?> updateCustomer(@PathVariable String cusId, @RequestBody UpdateCustomerRequest request) {
        try {
            Customer updatedCustomer = customerService.updateCustomer(cusId, request);
            return ResponseEntity.ok(updatedCustomer);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/profiles")
    public ResponseEntity<List<CustomerUserProjection>> getAllCustomerProfiles() {
        List<CustomerUserProjection> customerProfiles = customerService.getAllCustomerProfiles();
        if (customerProfiles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(customerProfiles);
    }
}