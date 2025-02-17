package com.swp.ChildrenVaccine.mapper;

import com.swp.ChildrenVaccine.dto.request.appointment.AppointmentRegisterRequest;
import com.swp.ChildrenVaccine.entities.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AppointmentMapper {
    AppointmentMapper INSTANCE = Mappers.getMapper(AppointmentMapper.class);

    AppointmentRegisterRequest toDTO(Appointment appointment);
    Appointment toEntity(AppointmentRegisterRequest appointmentRegisterRequest);
}