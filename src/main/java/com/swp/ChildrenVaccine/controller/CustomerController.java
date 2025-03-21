package com.swp.ChildrenVaccine.controller;

import com.swp.ChildrenVaccine.dto.request.CustomerUserDTO;
import com.swp.ChildrenVaccine.entities.Customer;
import com.swp.ChildrenVaccine.repository.CustomerRepository;
import com.swp.ChildrenVaccine.repository.CustomerUserProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/{userId}/profile")
    public ResponseEntity<CustomerUserProjection> getCustomerProfile(@PathVariable String userId) {
        CustomerUserProjection customerUserDTO = customerRepository.findCustomerProfile(userId);
        if (customerUserDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customerUserDTO);
    }

    @GetMapping("/first")
    public ResponseEntity<CustomerUserProjection> getFirstCustomer() {
        CustomerUserProjection firstCustomer = customerRepository.findFirstCustomerNative();
        if (firstCustomer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(firstCustomer);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Customer> getCustomer(@PathVariable Long id) {
//        Customer customer = customerRepository.findById(id).orElse(null);
//        if (customer == null) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(customer);
//    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customerDetails) {
//        Customer customer = customerRepository.findById(id).orElse(null);
//        if (customer == null) {
//            return ResponseEntity.notFound().build();
//        }
//        customer.setEmail(customerDetails.getEmail());
//        customer.setPassword(customerDetails.getPassword());
//        customer.setFullName(customerDetails.getFullName());
//        customer.setPhone(customerDetails.getPhone());
//        customer.setAddress(customerDetails.getAddress());
//        Customer updatedCustomer = customerRepository.save(customer);
//        return ResponseEntity.ok(updatedCustomer);
//    }
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
//        Customer customer = customerRepository.findById(id).orElse(null);
//        if (customer == null) {
//            return ResponseEntity.notFound().build();
//        }
//        customerRepository.delete(customer);
//        return ResponseEntity.noContent().build();
//    }
}