package com.swp.ChildrenVaccine.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;


@Getter
@Setter
@Data
public class RescheduleAppointmentRequest {
    @NotNull(message = "Ngày hẹn mới không được để trống")
    private LocalDate newDate;

    @NotNull(message = "Giờ hẹn mới không được để trống")
    private LocalTime newTime;
}
