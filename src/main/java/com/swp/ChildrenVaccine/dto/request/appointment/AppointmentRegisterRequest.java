package com.swp.ChildrenVaccine.dto.request.appointment;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.swp.ChildrenVaccine.entities.Child;
import com.swp.ChildrenVaccine.entities.Customer;
import com.swp.ChildrenVaccine.entities.Vaccine;
import com.swp.ChildrenVaccine.entities.VacinePackage;
import com.swp.ChildrenVaccine.enums.AppStatus;
import com.swp.ChildrenVaccine.enums.PaymentStatus;
import jakarta.persistence.*;
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
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate appointmentDate;

        @NotNull
        @JsonFormat(pattern = "HH:mm:ss")
        private LocalTime appointmentTime;

        // Đảm bảo chỉ có 1 trong 2 giá trị được điền
        @AssertTrue(message = "Chỉ được chọn một trong vaccineId hoặc packageId")
        private boolean isValidServiceSelection() {
                return (vaccineId != null && packageId == null) || (vaccineId == null && packageId != null);
        }public class Appointment {

                @Id
                @Column(name = "app_id", length = 50)
                private String appId;

                @ManyToOne
                @JoinColumn(name = "customer_id", nullable = false)
                private Customer customerId;

                @OneToOne
                @JoinColumn(name = "child_id")
                private Child childId;

                @Column(name = "appointment_date", nullable = false)
                private LocalDate appointmentDate;

                @Column(name = "appointment_time", nullable = false)
                private LocalTime appointmentTime;

                @Column(name = "status", length = 15, nullable = false)
                @Enumerated(EnumType.STRING)
                private AppStatus status;

                @Column(name = "payment_status", length = 10, nullable = false)
                @Enumerated(EnumType.STRING)
                private PaymentStatus paymentStatus;

                @OneToOne
                @JoinColumn(name = "vaccine_id")
                private Vaccine vaccineId;

                @OneToOne
                @JoinColumn(name = "package_id")
                private VacinePackage packageId;

        }

}
