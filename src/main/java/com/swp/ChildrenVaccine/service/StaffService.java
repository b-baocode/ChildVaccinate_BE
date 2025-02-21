package com.swp.ChildrenVaccine.service;

import com.swp.ChildrenVaccine.entities.Staff;
import com.swp.ChildrenVaccine.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StaffService {
    private final StaffRepository staffRepository;

    public Staff findByEmail(String email) {
        return staffRepository.findByUserEmail(email).orElse(null);
    }
}
