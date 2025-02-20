package com.swp.ChildrenVaccine.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.swp.ChildrenVaccine.entities.Appointment;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Getter
@Setter
public class FeedbackRequest {
    @JsonProperty("appointmentId")
    private String appointmentId;

    @JsonProperty("appointmentDate")
    private LocalDate appointmentDate;

    @JsonProperty("appointmentTime")
    private LocalTime appointmentTime;

    @JsonProperty("child")
    private String child;

    @JsonProperty("vaccine")
    private String vaccine;

    @JsonProperty("rating")
    private int rating;

    @JsonProperty("feedback")
    private String feedback;

}
