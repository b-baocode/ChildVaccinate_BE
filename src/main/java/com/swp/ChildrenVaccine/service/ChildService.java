package com.swp.ChildrenVaccine.service;

public class ChildService {
    public List<Child> getChildrenByCustomerId(String customerId) {
        return childRepository.findByCusId(customerId);
    }
}
