package com.swp.ChildrenVaccine.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VaccineRequest {

    @NotNull
    private String name;

    private String description;

    private String manufacturer;

    @NotNull
    private int shotNumber;

    @NotNull
    private int quantity;

    @NotNull
    private BigDecimal price;

    private int gapDays;
}