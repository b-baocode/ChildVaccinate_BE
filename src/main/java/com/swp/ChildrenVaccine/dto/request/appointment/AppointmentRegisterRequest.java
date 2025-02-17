package com.swp.ChildrenVaccine.dto.request.appointment;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.AssertTrue;
import lombok.*;
import lombok.experimental.FieldDefaults;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonInclude;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppointmentRegisterRequest {
        @NotNull
        private String customerId;

        @NotNull
        private String childId;

        private String vaccineId;
        private String packageId;

        @NotNull
        private LocalDate appointmentDate;

        @NotNull
        private LocalTime appointmentTime;

        // Đảm bảo chỉ có 1 trong 2 giá trị được điền
        @AssertTrue(message = "Chỉ được chọn một trong vaccineId hoặc packageId")
        public boolean isValidServiceSelection() {
            return (vaccineId == null && packageId != null) || (vaccineId != null && packageId == null);
        }

}
