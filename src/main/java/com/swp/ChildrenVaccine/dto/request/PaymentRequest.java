package com.swp.ChildrenVaccine.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
public class PaymentRequest {
    private String scheduleId;
    private String appointmentId;
    @JsonProperty("payFull")
    private boolean payFull;

}
