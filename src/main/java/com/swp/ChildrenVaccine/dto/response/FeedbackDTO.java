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
    private String feedbackText;
    private String customerFullName;
    private String appointmentDate;

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
