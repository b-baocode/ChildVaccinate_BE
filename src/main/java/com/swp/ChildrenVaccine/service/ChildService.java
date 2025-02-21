package com.swp.ChildrenVaccine.service;

import com.swp.ChildrenVaccine.entities.Child;
import com.swp.ChildrenVaccine.repository.ChildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ChildService {
    private final ChildRepository childRepository;

    public List<Child> getAllChildren() {
        return childRepository.findAll();
    }

    public Child getChildById(String childId) {
        return childRepository.findById(childId).
                orElseThrow(() -> new RuntimeException("Không tìm thấy trẻ với ID: " + childId));
    }

}