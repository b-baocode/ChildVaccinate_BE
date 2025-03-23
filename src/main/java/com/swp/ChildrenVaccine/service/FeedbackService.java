package com.swp.ChildrenVaccine.service;

import com.swp.ChildrenVaccine.dto.response.FeedbackDTO;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    @Autowired
    private final AppointmentRepository appointmentRepository;

    @Autowired
    private final FeedbackRepository feedbackRepository;

    @Autowired
    private final CustomerRepository customerRepository;

    @Transactional
    public FeedbackDTO saveFeedback(FeedbackRequest request, HttpSession session) {
        // Check if customer exists in session
        Customer customer = (Customer) session.getAttribute("loggedInCustomer");

        if (customer == null) {
            throw new RuntimeException("Không tìm thấy khách hàng trong session! Vui lòng đăng nhập lại.");
        }

        // Validate and retrieve appointment
        Appointment appointment = appointmentRepository.findByAppId(request.getAppointmentId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy cuộc hẹn phù hợp!"));

        // Create and save new feedback
        RatingFeedback feedback = new RatingFeedback();
        feedback.setId(generateFeedbackId());
        feedback.setAppointment(appointment);
        feedback.setCustomer(customer);
        feedback.setRating(request.getRating());
        feedback.setFeedback(request.getFeedback());

        RatingFeedback savedFeedback = feedbackRepository.saveAndFlush(feedback);

        // Convert to DTO for response
        return convertToDTO(savedFeedback);
    }

    public List<FeedbackDTO> getAllFeedback() {
        List<RatingFeedback> feedbackList = feedbackRepository.findAll();
        return feedbackList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private String generateFeedbackId() {
        Optional<RatingFeedback> lastFeedback = feedbackRepository.findTopByOrderByIdDesc();

        if (lastFeedback.isPresent()) {
            String lastId = lastFeedback.get().getId(); // e.g., "FB003"
            int number = Integer.parseInt(lastId.substring(2)) + 1; // Extract number and increment
            return String.format("FB%03d", number);
        }
        return "FB001"; // First ID if no feedback exists
    }

    private FeedbackDTO convertToDTO(RatingFeedback feedback) {
        FeedbackDTO dto = new FeedbackDTO();
        dto.setFeedbackId(feedback.getId());
        dto.setAppointmentId(feedback.getAppointment().getAppId());
        dto.setCustomerId(feedback.getCustomer().getCusId());
        dto.setRating(feedback.getRating());
        dto.setFeedbackText(feedback.getFeedback());
        dto.setCustomerFullName(feedback.getCustomer().getUser().getFullName()); // Optional: Include customer name
        dto.setAppointmentDate(feedback.getAppointment().getAppointmentDate().toString()); // Optional: Include appointment date
        return dto;
    }
}