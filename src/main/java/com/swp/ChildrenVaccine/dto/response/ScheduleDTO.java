package com.swp.ChildrenVaccine.dto.response;

import com.swp.ChildrenVaccine.entities.Schedule;
import com.swp.ChildrenVaccine.enums.ScheduleStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ScheduleDTO {
    private String scheduleId;
    private String cusId;
    private String cusName;
    private String childName;
    private String vaccineName;
    private String packageName;
    private LocalDate startDate;
    private int totalShot;
    private ScheduleStatus status;

    public ScheduleDTO(Schedule schedule) {
        this.scheduleId = schedule.getScheduleId();
        this.cusId = schedule.getCustomer().getCusId();
        this.cusName = schedule.getCustomer().getUser().getFullName();
        this.childName = schedule.getChild().getFullName();
        this.vaccineName = schedule.getVaccine() != null ? schedule.getVaccine().getName() : null;
        this.packageName = schedule.getAPackage() != null ? schedule.getAPackage().getName() : null;
        this.startDate = schedule.getStartDate();
        this.totalShot = schedule.getTotalShot();
        this.status = schedule.getStatus();
    }
}