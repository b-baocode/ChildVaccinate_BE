package com.swp.ChildrenVaccine.service;

import com.swp.ChildrenVaccine.dto.response.ChildDTO;
import com.swp.ChildrenVaccine.entities.Child;
import com.swp.ChildrenVaccine.entities.Customer;
import com.swp.ChildrenVaccine.repository.ChildRepository;
import com.swp.ChildrenVaccine.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChildService {

    private CustomerRepository customerRepository;

    private final ChildRepository childRepository;

    public ChildService(ChildRepository childRepository) {
        this.childRepository = childRepository;
    }

    @Autowired
    public ChildService(ChildRepository childRepository, CustomerRepository customerRepository) {
        this.childRepository = childRepository;
        this.customerRepository = customerRepository;
    }

    public List<ChildDTO> getChildrenByCustomerId(String customerId) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null) {
            throw new RuntimeException("Customer not found with ID: " + customerId);
        }
        List<Child> children = childRepository.getChildrenByCusId(customer);
        return children.stream().map(ChildDTO::new).collect(Collectors.toList());
    }

    public void addChild(String cusId, @RequestBody Child child) {
        Child newChild = new Child();
        newChild.setChildId(generateChildId());

        Customer customer = new Customer();
        customer.setCusId(cusId);
        newChild.setCusId(customer);

        newChild.setFullName(child.getFullName());
        newChild.setGender(child.getGender());
        newChild.setDateOfBirth(child.getDateOfBirth());
        newChild.setWeight(child.getWeight());
        newChild.setHeight(child.getHeight());
        newChild.setBloodType(child.getBloodType());

        childRepository.save(newChild); // Save the child information to the database
    }


    public String generateChildId() {
        Optional<Child> lastChild = childRepository.findTopByOrderByChildId();
        if (lastChild.isPresent()) {
            String lastId = lastChild.get().getChildId(); // VD: "CHI001"
            int number = Integer.parseInt(lastId.substring(3)) + 1;
            return String.format("CH%03d", number);
        }
        return "CH001"; // ID đầu tiên
    }

    public void updateChild(String id, Child child) {
        Child childToUpdate = childRepository.findById(id).orElse(null);
        if (childToUpdate == null) {
            return;
        }
        childToUpdate.setFullName(child.getFullName());
        childToUpdate.setWeight(child.getWeight());
        childToUpdate.setHeight(child.getHeight());
        childToUpdate.setHealthNote(child.getHealthNote());
        childRepository.save(childToUpdate);
    }
}