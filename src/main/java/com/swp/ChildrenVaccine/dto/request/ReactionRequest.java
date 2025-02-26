package com.swp.ChildrenVaccine.dto.request;

import com.swp.ChildrenVaccine.enums.Severity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@Data
public class ReactionRequest {
    private String childId;
    private String appointmentId;
    private String symptoms;
    private Severity severity;
    private LocalDateTime reactionDate;


}
