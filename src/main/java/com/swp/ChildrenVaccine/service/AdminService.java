package com.swp.ChildrenVaccine.service;

import com.swp.ChildrenVaccine.entities.Admin;
import com.swp.ChildrenVaccine.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;

    public Admin findByEmail(String email) {
        return adminRepository.findByUserEmail(email).orElse(null);
    }
}
