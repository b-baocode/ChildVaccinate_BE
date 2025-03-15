package com.swp.ChildrenVaccine.service;

import com.swp.ChildrenVaccine.entities.Vaccine;
import com.swp.ChildrenVaccine.repository.AppointmentRepository;
import com.swp.ChildrenVaccine.repository.StaffRepository;
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

    public List<Vaccine> getTop5Vaccines() {
        return appointmentRepository.findTop5Vaccines(PageRequest.of(0, 5));
    }



}
