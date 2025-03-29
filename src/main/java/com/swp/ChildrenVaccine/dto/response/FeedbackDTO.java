package com.swp.ChildrenVaccine.dto.response;

import com.swp.ChildrenVaccine.entities.RatingFeedback;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDTO {
    private String feedbackId;
    private String appointmentId;
    private String customerId;
    private int rating;
    private String feedbackText; // Rename 'feedback' to 'feedbackText' to avoid confusion with the field name
    private String customerFullName; // Optional: Include customer name for display
    private String appointmentDate; // Optional: Include appointment date for context

    public FeedbackDTO(RatingFeedback feedback) {
        this.feedbackId = feedback.getId();
        this.appointmentId = feedback.getAppointment().getAppId();
        this.customerId = feedback.getCustomer().getCusId();
        this.rating = feedback.getRating();
        this.feedbackText = feedback.getFeedback();
        this.customerFullName = feedback.getCustomer().getUser().getFullName();
        this.appointmentDate = feedback.getAppointment().getAppointmentDate().toString();
    }

}