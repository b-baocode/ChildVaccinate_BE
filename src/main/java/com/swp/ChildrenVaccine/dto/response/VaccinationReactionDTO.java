package com.swp.ChildrenVaccine.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class VaccinationReactionDTO {
    private String id;
    private String childId;
    private String childName;
    private String appointmentId;
    private String symptoms;
    private String severity;
    private LocalDateTime reactionDate;
}

