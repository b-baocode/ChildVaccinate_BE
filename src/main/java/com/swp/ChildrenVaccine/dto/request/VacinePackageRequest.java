package com.swp.ChildrenVaccine.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VacinePackageRequest {

    @NotNull
    private String name;

    private String description;

    @NotNull
    private int available;

    @NotNull
    private BigDecimal price;

    private List<String> vaccineIds;
}