package com.swp.ChildrenVaccine.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TimeSlotAvailabilityDTO {
    private int currentCount;
    private int maxAllowed;
    private boolean available;
    private String timeSlot;
    private String date;
}