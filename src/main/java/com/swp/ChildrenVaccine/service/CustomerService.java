package com.swp.ChildrenVaccine.service;

import com.swp.ChildrenVaccine.dto.request.UpdateCustomerRequest;
import com.swp.ChildrenVaccine.entities.Customer;
import com.swp.ChildrenVaccine.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public String generateCustomerId() {
        Optional<Customer> lastCustomer = customerRepository.findTopByOrderByCusIdDesc();
        if (lastCustomer.isPresent()) {
            String lastId = lastCustomer.get().getCusId(); // VD: "CUS001"
            int number = Integer.parseInt(lastId.substring(3)) + 1;
            return String.format("CUS%03d", number);
        }
        return "CUS001"; // ID đầu tiên
    }

    public Customer findByEmail(String email) {
        Optional<Customer> customer = customerRepository.findByUser_Email(email);
        return customer.orElse(null);
    }

    public Customer updateCustomer(String cusId, UpdateCustomerRequest request) {
        return customerRepository.findByCusId(cusId)
                .map(customer -> {
                    if(request.getPhone() != null) {
                        customer.getUser().setPhone(request.getPhone());
                    }
                    if(request.getAddress() != null) {
                        customer.setAddress(request.getAddress());
                    }
                    return customerRepository.save(customer);
                })
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + cusId));
    }

}
