package com.swp.ChildrenVaccine.config;

import com.swp.ChildrenVaccine.service.AppointmentService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppointmentStartupConfig {

    @Bean
    public ApplicationRunner runOnStartup(AppointmentService appointmentService) {
        return args -> appointmentService.checkAndCancelExpiredAppointments();
    }
}
