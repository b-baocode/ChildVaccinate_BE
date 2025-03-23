package com.swp.ChildrenVaccine.dto.response;

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
}