package com.swp.ChildrenVaccine.controller;

import com.swp.ChildrenVaccine.dto.request.UpdateCustomerRequest;
import com.swp.ChildrenVaccine.entities.Customer;
import com.swp.ChildrenVaccine.repository.CustomerRepository;
import com.swp.ChildrenVaccine.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @PostMapping("/customers")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
        return ResponseEntity.ok(savedCustomer);
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable String id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customer);
    }

    @PutMapping("/{cusId}/update")
    public ResponseEntity<?> updateCustomer(@PathVariable String cusId, @RequestBody UpdateCustomerRequest request) {
        try {
            Customer updatedCustomer = customerService.updateCustomer(cusId, request);
            return ResponseEntity.ok(updatedCustomer);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

//    @PutMapping("/customers/{id}")
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
//    @DeleteMapping("/customers/{id}")
//    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
//        Customer customer = customerRepository.findById(id).orElse(null);
//        if (customer == null) {
//            return ResponseEntity.notFound().build();
//        }
//        customerRepository.delete(customer);
//        return ResponseEntity.noContent().build();
//    }
}
