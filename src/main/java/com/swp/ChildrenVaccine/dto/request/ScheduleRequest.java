package com.swp.ChildrenVaccine.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import com.fasterxml.jackson.annotation.JsonInclude;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScheduleRequest {

    @NotNull
    private String customerId;

    @NotNull
    private String childId;

    private String vaccineId;
    private String packageId;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime firstAppTime;

    @AssertTrue(message = "Chỉ được chọn một trong vaccineId hoặc packageId")
    private boolean isValidServiceSelection() {
        return (vaccineId != null && packageId == null) || (vaccineId == null && packageId != null);
    }
}