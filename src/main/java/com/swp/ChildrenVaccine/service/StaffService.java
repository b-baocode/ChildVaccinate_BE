package com.swp.ChildrenVaccine.service;

import com.swp.ChildrenVaccine.entities.Staff;
import com.swp.ChildrenVaccine.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StaffService {
    private final StaffRepository staffRepository;

    public Staff findByEmail(String email) {
        return staffRepository.findByUserEmail(email).orElse(null);
    }

    public String generateStaffId() {
        Optional<Staff> lastStaff = staffRepository.findTopByOrderByIdDesc();
        if (lastStaff.isPresent()) {
            String lastId = lastStaff.get().getId(); // VD: "U003"
            int number = Integer.parseInt(lastId.substring(1)) + 1;
            return String.format("S%03d", number);
        }
        return "S001"; // ID đầu tiên
    }

    public List<Staff> getAllStaffs() {
        return staffRepository.findAll();
    }
}
