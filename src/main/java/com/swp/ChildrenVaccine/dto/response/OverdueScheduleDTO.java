package com.swp.ChildrenVaccine.dto.response;

import com.swp.ChildrenVaccine.entities.Appointment;
import com.swp.ChildrenVaccine.entities.Schedule;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OverdueScheduleDTO {
    private Schedule schedule;
    private Appointment overdueAppointment;

    public OverdueScheduleDTO(Schedule schedule, Appointment overdueAppointment) {
        this.schedule = schedule;
        this.overdueAppointment = overdueAppointment;
    }
}