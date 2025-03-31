package com.swp.ChildrenVaccine.service;

import com.swp.ChildrenVaccine.entities.Staff;
import com.swp.ChildrenVaccine.entities.User;
import com.swp.ChildrenVaccine.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StaffService {
    @Autowired
    private StaffRepository staffRepository;

    public List<Staff> getAllStaffs() {
        return staffRepository.findAll();
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
}
