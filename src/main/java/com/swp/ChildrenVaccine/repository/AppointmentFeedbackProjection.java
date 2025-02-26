package com.swp.ChildrenVaccine.repository;

import java.time.LocalDate;
import java.time.LocalTime;

public interface AppointmentFeedbackProjection {
    String getAppId();
    LocalDate getAppointmentDate();
    LocalTime getAppointmentTime();
    String getChildName();
    String getVaccineName();
}

