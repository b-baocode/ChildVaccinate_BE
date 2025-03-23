package com.swp.ChildrenVaccine.service;


import com.swp.ChildrenVaccine.dto.request.StaffRequest;
import com.swp.ChildrenVaccine.entities.Staff;
import com.swp.ChildrenVaccine.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {

    @Autowired
    private StaffRepository staffRepository;

    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    public Staff getStaffById(String id) {
        return staffRepository.findById(id).orElse(null);
    }

//    public Staff createStaff(Staff staff) {
//        return staffRepository.save(staff);
//    }

    public Staff updateStaff(String id, StaffRequest staffRequest) {
        return staffRepository.findById(id).map(staff -> {
            staff.setDepartment(staffRequest.getDepartment());
            staff.setHireDate(staffRequest.getHireDate());
            staff.setQualification(staffRequest.getQualification());
            staff.setSpecialization(staffRequest.getSpecialization());
            return staffRepository.save(staff);
        }).orElse(null);
    }
    public boolean deleteStaff(String id) {
        return staffRepository.findById(id).map(staff -> {
            staff.getUser().setActive(false);
            staffRepository.save(staff);
            return true;
        }).orElse(false);
    }
}