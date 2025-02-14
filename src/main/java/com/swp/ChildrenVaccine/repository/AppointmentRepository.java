package com.swp.ChildrenVaccine.repository;

import com.swp.ChildrenVaccine.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, String> {
}