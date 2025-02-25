package com.swp.ChildrenVaccine.service;

import com.swp.ChildrenVaccine.dto.request.FeedbackRequest;
import com.swp.ChildrenVaccine.entities.Appointment;
import com.swp.ChildrenVaccine.entities.Customer;
import com.swp.ChildrenVaccine.entities.RatingFeedback;
import com.swp.ChildrenVaccine.entities.User;
import com.swp.ChildrenVaccine.repository.AppointmentRepository;
import com.swp.ChildrenVaccine.repository.CustomerRepository;
import com.swp.ChildrenVaccine.repository.FeedbackRepository;
import jakarta.transaction.Transactional;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public void saveFeedback(FeedbackRequest request, HttpSession session) {
        Customer customer = (Customer) session.getAttribute("loggedInCustomer");

        if (customer == null) {
            throw new RuntimeException("Không tìm thấy khách hàng trong session! Vui lòng đăng nhập lại.");
        }

        Appointment appointment = appointmentRepository.findByAppId(request.getAppointmentId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy cuộc hẹn phù hợp!"));

        RatingFeedback feedback = new RatingFeedback();
        feedback.setId(generateFeedbackId());
        feedback.setAppointment(appointment);
        feedback.setCustomer(customer);
        feedback.setRating(request.getRating());
        feedback.setFeedback(request.getFeedback());
        feedbackRepository.saveAndFlush(feedback);  // Gọi saveAndFlush thay vì save()
    }

    public String generateFeedbackId() {
        Optional<RatingFeedback> lastFeedback = feedbackRepository.findTopByOrderByIdDesc();

        if (lastFeedback.isPresent()) {
            String lastId = lastFeedback.get().getId(); // VD: "FB003"
            int number = Integer.parseInt(lastId.substring(2)) + 1; // Lấy số thứ tự và tăng lên
            return String.format("FB%03d", number);
        }
        return "FB001"; // ID đầu tiên nếu chưa có feedback nào
    }

    public List<RatingFeedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }
}
