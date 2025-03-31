package com.swp.ChildrenVaccine.service;

import com.swp.ChildrenVaccine.dto.request.CreateStaffRequest;
import com.swp.ChildrenVaccine.dto.request.RegisterRequest;
import com.swp.ChildrenVaccine.entities.Customer;
import com.swp.ChildrenVaccine.entities.Staff;
import com.swp.ChildrenVaccine.entities.User;
import com.swp.ChildrenVaccine.entities.Vaccine;
import com.swp.ChildrenVaccine.enums.Gender;
import com.swp.ChildrenVaccine.enums.RoleEnum;
import com.swp.ChildrenVaccine.exception.EmailAlreadyExistsException;
import com.swp.ChildrenVaccine.repository.AppointmentRepository;
import com.swp.ChildrenVaccine.repository.StaffRepository;
import com.swp.ChildrenVaccine.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;

@Service
public class AdminService {
    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private StaffService staffService;

    public long getNumberOfStaff() {
        return staffRepository.count();
    }

    public long getNumberOfAppointmentsForToday() {
        LocalDate today = LocalDate.now();
        return appointmentRepository.countByAppointmentDate(today);
    }

    public String getTotalRevenue() {
        double totalRevenue = appointmentRepository.getTotalRevenueVac() + appointmentRepository.getTotalRevenuePack();
        DecimalFormat decimalFormat = new DecimalFormat("#");
        decimalFormat.setMaximumFractionDigits(0);
        return decimalFormat.format(totalRevenue);
    }

    public List<?> getTop5Vaccines() {
        return appointmentRepository.findTop5Vaccines();
    }

    public void createStaff(CreateStaffRequest request){
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email đã tồn tại. Vui lòng nhập email khác!");
        }

        String userId = userService.generateUserId();
        String staffId = staffService.generateStaffId();

        User user = new User();
        user.setId(userId);
        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());
        user.setPassword(request.getPassword()); // Mã hóa mật khẩu
        user.setPhone(request.getPhone());
        user.setRole(RoleEnum.STAFF);
        user.setActive(true);

        Staff staff = new Staff();
        staff.setId(staffId);
        staff.setUser(user);
        staff.setDepartment(request.getDepartment());
        staff.setHireDate(request.getHireDate());
        staff.setSpecialization(request.getSpecialization());
        staff.setQualification(request.getQualification());

        userRepository.save(user);
        staffRepository.save(staff);
    }

    @Transactional
    public void updateStaff(String id, CreateStaffRequest request) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        User user = staff.getUser();
        user.setFullName(request.getFullName());
        // Only update password if provided
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(request.getPassword());
        }
        user.setPhone(request.getPhone());

        staff.setDepartment(request.getDepartment());
        staff.setHireDate(request.getHireDate());
        staff.setSpecialization(request.getSpecialization());
        staff.setQualification(request.getQualification());

        userRepository.save(user);
        staffRepository.save(staff);
    }

    @Transactional
    public void deleteStaff(String id) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        staffRepository.delete(staff);
        userRepository.delete(staff.getUser());

    }


}
