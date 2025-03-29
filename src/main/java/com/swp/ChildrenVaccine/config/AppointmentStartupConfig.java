package com.swp.ChildrenVaccine.config;

import com.swp.ChildrenVaccine.service.AppointmentService;
import com.swp.ChildrenVaccine.service.ScheduleService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppointmentStartupConfig {

    @Bean
    public ApplicationRunner runOnStartup(AppointmentService appointmentService, ScheduleService scheduleService) {
        return args -> {
            appointmentService.sendReminderEmailsForUpcomingAppointments();
        };
    }
}
