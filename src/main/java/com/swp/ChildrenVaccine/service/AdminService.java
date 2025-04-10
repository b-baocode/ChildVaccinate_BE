package com.swp.ChildrenVaccine.service;

import com.swp.ChildrenVaccine.dto.request.CreateStaffRequest;
import com.swp.ChildrenVaccine.dto.response.VaccineResponseDTO;
import com.swp.ChildrenVaccine.entities.*;
import com.swp.ChildrenVaccine.enums.RoleEnum;
import com.swp.ChildrenVaccine.exception.EmailAlreadyExistsException;
import com.swp.ChildrenVaccine.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;

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
    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private VaccineRepository vaccineRepository;

    public long getNumberOfStaff() {
        return staffRepository.count();
    }

    public Admin findByEmail(String email) {
        return adminRepository.findByUserEmail(email).orElse(null);
    }

    public long getNumberOfAppointmentsForToday() {
        LocalDate today = LocalDate.now();
        return appointmentRepository.countByAppointmentDate(today);
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

    public List<RatingFeedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    public List<Appointment> getAppointmentOfSchedule(String scheduleId) {
        List<Appointment> appointments = appointmentRepository.findByScheduleId(scheduleId);
        for (Appointment appointment : appointments) {
            System.out.println(appointment.getAppointmentDate());
        }
        return appointments;
    }

    public double getRevenueOfSchedule(String scheduleId) {
        double totalRevenue = 0;
        List<Appointment> appointments = appointmentRepository.findByScheduleId(scheduleId);
        for (Appointment appointment : appointments) {
            if (appointment.getVaccine() != null) {
                totalRevenue += appointment.getVaccine().getPrice().doubleValue(); //* appointment.getVaccine().getShotNumber();
            }
        }
        return totalRevenue;
    }

    public String getTotalRevenue() {
        double totalRevenue = 0;
        List<Appointment> appointments = appointmentRepository.findPaidAppointments();
        for (Appointment appointment : appointments) {
            if (appointment.getVaccine() != null) {
                totalRevenue += appointment.getVaccine().getPrice().doubleValue();
            }
        }
        DecimalFormat decimalFormat = new DecimalFormat("#");
        decimalFormat.setMaximumFractionDigits(0);
        return decimalFormat.format(totalRevenue);
    }

    public List<VaccineResponseDTO> getTop5Vaccines() {
        Pageable topFive = PageRequest.of(0, 100);
        List<Object[]> top5Vaccines = appointmentRepository.findTop5Vaccines(topFive);
        return top5Vaccines.stream()
                .map(result -> {
                    String vaccineName = (String) result[0];
                    long count = ((Number) result[1]).longValue();
                    double revenue = ((BigDecimal) result[2]).doubleValue();
                    return new VaccineResponseDTO( vaccineName, (int) count, revenue  );
                })
                .collect(Collectors.toList());
    }

}
