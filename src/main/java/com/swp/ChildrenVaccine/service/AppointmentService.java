package com.swp.ChildrenVaccine.service;

import com.swp.ChildrenVaccine.dto.request.RescheduleAppointmentRequest;
import com.swp.ChildrenVaccine.dto.request.ScheduleRequest;
import com.swp.ChildrenVaccine.dto.request.appointment.AppointmentRegisterRequest;
import com.swp.ChildrenVaccine.dto.response.AppointmentDTO;
import com.swp.ChildrenVaccine.dto.response.AppointmentSimpleDTO;
import com.swp.ChildrenVaccine.dto.response.TimeSlotAvailabilityDTO;
import com.swp.ChildrenVaccine.entities.*;
import com.swp.ChildrenVaccine.enums.AppStatus;
import com.swp.ChildrenVaccine.enums.MailNoticeStatus;
import com.swp.ChildrenVaccine.enums.PaymentStatus;

import com.swp.ChildrenVaccine.enums.ScheduleStatus;
import com.swp.ChildrenVaccine.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class AppointmentService {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentService.class);
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private VaccinationRelationRepository vaccinationRelationRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private ScheduleService scheduleService;

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ChildRepository childRepository;

    @Autowired
    private VaccineRepository vaccineRepository;

    @Autowired
    private VaccinePackageRepository vaccinePackageRepository;

    public void createAppointmentsForSchedule(Schedule schedule, ScheduleRequest request) {
        List<Appointment> appointments = new ArrayList<>();

        if (request.getVaccineId() != null) {
            Vaccine vaccine = vaccineRepository.findById(request.getVaccineId())
                    .orElseThrow(() -> new RuntimeException("Vaccine not found"));
            createAppointmentsForVaccine(appointments, schedule, vaccine, request.getStartDate(), request.getFirstAppTime());
        } else if (request.getPackageId() != null) {
            VacinePackage aPackage = vaccinePackageRepository.findById(request.getPackageId())
                    .orElseThrow(() -> new RuntimeException("Package not found"));
            createAppointmentsForPackage(appointments, schedule, aPackage, request.getStartDate(), request.getFirstAppTime());
        }

        appointmentRepository.saveAll(appointments);
    }

    private void createAppointmentsForVaccine(List<Appointment> appointments, Schedule schedule, Vaccine vaccine, LocalDate startDate, LocalTime startTime) {
        LocalDate appointmentDate = startDate; // Bắt đầu từ ngày startDate
        String lastId = appointmentRepository.findMaxAppId();
        int newId = (lastId != null && lastId.matches("APP\\d+")) ? Integer.parseInt(lastId.replace("APP", "")) + 1 : 1;

        for (int i = 1; i <= vaccine.getShotNumber(); i++) {
            Appointment appointment = new Appointment();
            appointment.setCustomer(schedule.getCustomer());
            appointment.setChild(schedule.getChild());
            appointment.setSchedule(schedule);
            appointment.setVaccine(vaccine);
            appointment.setAppointmentDate(appointmentDate);
            appointment.setAppointmentTime(startTime);
            appointment.setShotNumber(i);
            vaccine.setQuantity(vaccine.getQuantity() - 1);
            vaccineRepository.save(vaccine);
            appointment.setStatus(AppStatus.CONFIRMED);
            appointment.setPaymentStatus(PaymentStatus.PENDING);
            appointment.setMailNotice(MailNoticeStatus.PENDING);

            // Generate new appId
            appointment.setAppId(String.format("APP%03d", newId++));

            appointments.add(appointment);

            // Cập nhật ngày cho lần hẹn tiếp theo
            appointmentDate = appointmentDate.plusDays(vaccine.getGapDays());
        }
    }

    private void createAppointmentsForPackage(List<Appointment> appointments, Schedule schedule, VacinePackage aPackage, LocalDate startDate, LocalTime startTime) {
        List<VaccinationRelation> relations = vaccinationRelationRepository.findByPackageId(aPackage.getPackageId());
        LocalDate appointmentDate = startDate;
        String lastId = appointmentRepository.findMaxAppId();
        int newId = (lastId != null && lastId.matches("APP\\d+")) ? Integer.parseInt(lastId.replace("APP", "")) + 1 : 1;

        for (VaccinationRelation relation : relations) {
            Vaccine vaccine = relation.getVaccine();
            for (int i = 1; i <= vaccine.getShotNumber(); i++) {
                Appointment appointment = new Appointment();
                appointment.setCustomer(schedule.getCustomer());
                appointment.setChild(schedule.getChild());
                appointment.setSchedule(schedule);
                appointment.setVaccine(vaccine);
                vaccine.setQuantity(vaccine.getQuantity() - 1);
                vaccineRepository.save(vaccine);
                appointment.setAppointmentDate(appointmentDate);
                appointment.setAppointmentTime(startTime);
                appointment.setShotNumber(i);
                appointment.setStatus(AppStatus.CONFIRMED);
                appointment.setPaymentStatus(PaymentStatus.PENDING);
                appointment.setMailNotice(MailNoticeStatus.PENDING);

                // Generate new appId
                appointment.setAppId(String.format("APP%03d", newId++));

                appointments.add(appointment);

                // Cập nhật ngày cho lần tiêm tiếp theo
                appointmentDate = appointmentDate.plusDays(vaccine.getGapDays());
            }
        }
    }

    public Appointment updateStatus(String appId, AppStatus newStatus) {
        Appointment appointment = appointmentRepository.findByAppId(appId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));

        AppStatus currentStatus = appointment.getStatus();

        if (currentStatus == AppStatus.CANCELLED) {
            throw new IllegalArgumentException("Cannot modify a cancelled appointment");
        }

        if (currentStatus == AppStatus.CONFIRMED &&
                (newStatus == AppStatus.COMPLETED || newStatus == AppStatus.CANCELLED)) {
            appointment.setStatus(newStatus);
        } else if (currentStatus == AppStatus.COMPLETED && newStatus == AppStatus.CANCELLED) {
            appointment.setStatus(newStatus);
            Vaccine vaccine = appointment.getVaccine();
            if (vaccine != null) {
                vaccine.setQuantity(vaccine.getQuantity() + 1);
                vaccineRepository.save(vaccine);
            }
            String scheduleId = appointment.getSchedule().getScheduleId();
            scheduleService.updateScheduleStatus(scheduleId, ScheduleStatus.CANCELLED);
        } else {
            throw new IllegalArgumentException("Invalid status transition");
        }

        return appointmentRepository.save(appointment);
    }

    //create thís for me getAppointmentById(appId)
    public Appointment getAppointmentById(String appId) {
        return appointmentRepository.findByAppId(appId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));
    }

    public List<AppointmentSimpleDTO> getCompletedAppointmentsWithoutFeedback(String cusId) {
        return appointmentRepository.findCompletedAppointmentsWithoutFeedback(cusId)
                .stream()
                .map(AppointmentSimpleDTO::new)
                .collect(Collectors.toList());
    }

    public List<AppointmentDTO> getAppointmentsByChildId(String childId) {
        List<Appointment> appointments = appointmentRepository.findByChildId(childId);
        return appointments.stream()
                .map(AppointmentDTO::new)
                .collect(Collectors.toList());
    }

    public AppointmentDTO getLatestAppointmentByChildId(String childId) {
        List<Appointment> appointments = appointmentRepository.findByChildId(childId);
        return appointments.stream()
                .filter(appointment -> appointment.getStatus() == AppStatus.COMPLETED)
                .max(Comparator.comparing(appointment -> LocalDateTime.of(appointment.getAppointmentDate(), appointment.getAppointmentTime())))
                .map(AppointmentDTO::new)
                .orElse(null);
    }

    public List<AppointmentDTO> getAppointmentsByPhoneNumber(String phoneNumber) {
        List<Appointment> appointments = appointmentRepository.findByPhoneNumber(phoneNumber);
        return appointments.stream()
                .filter(appointment -> appointment.getStatus() == AppStatus.CONFIRMED)
                .map(AppointmentDTO::new)
                .collect(Collectors.toList());
    }

    public List<Appointment> getAppointmentsByCustomerId(String cusId) {
        return appointmentRepository.findByCustomerId(cusId);
    }

    public TimeSlotAvailabilityDTO checkTimeSlotAvailability(String date, String timeSlot) {
        LocalDate appointmentDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        String formattedTimeSlot = LocalTime.parse(timeSlot, DateTimeFormatter.ISO_TIME)
                .format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        int currentCount = appointmentRepository.countByDateAndTimeSlot(appointmentDate, formattedTimeSlot);
        System.out.println("Current count: " + currentCount);

        int maxAllowed = 5;
        boolean available = currentCount < maxAllowed;

        return new TimeSlotAvailabilityDTO(currentCount, maxAllowed, available, timeSlot, date);
    }

    public List<AppointmentDTO> getAllAppointmentByScheduleId(String scheduleId) {
        List<Appointment> appointments = appointmentRepository.findByScheduleId(scheduleId);
        return appointments.stream()
                .map(AppointmentDTO::new)
                .collect(Collectors.toList());
    }

    public Appointment updatePaymentStatus(String appId) {
        Appointment appointment = appointmentRepository.findById(appId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));
        if (appointment.getPaymentStatus() == PaymentStatus.PENDING) {
            appointment.setPaymentStatus(PaymentStatus.PAID);
            updateStatus(appId, AppStatus.COMPLETED);
            return appointmentRepository.save(appointment);
        } else {
            throw new IllegalArgumentException("Payment status is not PENDING");
        }
    }

    public void sendReminderEmailsForUpcomingAppointments() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        List<Appointment> appointments = appointmentRepository.findAll();
        List<Appointment> upcomingAppointments = appointments.stream()
                .filter(a -> a.getAppointmentDate().isEqual(tomorrow) && a.getStatus() == AppStatus.CONFIRMED)
                .collect(Collectors.toList());

        for (Appointment appointment : upcomingAppointments) {
            sendAppointmentEmail(appointment);
        }
    }

    public String sendAppointmentEmail(Appointment appointment) {
        if (appointment.getMailNotice() == MailNoticeStatus.SENDED) {
            return "Appointment " + appointment.getAppId() + " email already sent.";
        }

        Customer customer = appointment.getCustomer();
        User user = customer.getUser();
        String email = user.getEmail();
        String subject = "Appointment Reminder at VNVC Clinic";

        String body = "<h3>Hello " + user.getFullName() + ",</h3>"
                + "<p>You have a confirmed appointment with the following details:</p>"
                + "<ul>"
                + "<li><b>Child Name:</b> " + appointment.getChild().getFullName() + "</li>"
                + "<li><b>Appointment Date:</b> " + appointment.getAppointmentDate() + "</li>"
                + "<li><b>Appointment Time:</b> " + appointment.getAppointmentTime() + "</li>"
                + (appointment.getVaccine() != null ? "<li><b>Vaccine:</b> " + appointment.getVaccine().getName() + "</li>" : "")
                + "</ul>"
                + "<p>Please arrive on time!</p>"
                + "<br><p>Regards,<br>VNVC Clinic</p>";

        emailService.sendAppointmentNotification(email, subject, body);

        appointment.setMailNotice(MailNoticeStatus.SENDED);
        appointmentRepository.save(appointment);

        return "Appointment " + appointment.getAppId() + " email sent successfully.";
    }

    public void checkAndCancelExpiredAppointments() {
        LocalDateTime now = LocalDateTime.now();

        List<Appointment> allAppointments = appointmentRepository.findAll();
        List<Appointment> expiredAppointments = allAppointments.stream()
                .filter(a -> LocalDateTime.of(a.getAppointmentDate(), a.getAppointmentTime()).isBefore(now)
                        && a.getStatus() == AppStatus.CONFIRMED)
                .toList();
        for (Appointment appointment : expiredAppointments) {
            appointment.setStatus(AppStatus.CANCELLED);
            appointmentRepository.save(appointment);

            // Gửi email thông báo hủy cuộc hẹn
            Customer customer = appointment.getCustomer();
            User user = customer.getUser();
            String email = user.getEmail();
            String subject = "Thông báo hủy cuộc hẹn tại VNVC";
            String body = "<h3>Xin chào " + user.getFullName() + ",</h3>"
                    + "<p>Cuộc hẹn của bạn đã bị hủy do quá hạn:</p>"
                    + "<ul>"
                    + "<li><b>Tên Trẻ:</b> " + appointment.getChild().getFullName() + "</li>"
                    + "<li><b>Ngày hẹn:</b> " + appointment.getAppointmentDate() + "</li>"
                    + "<li><b>Giờ hẹn:</b> " + appointment.getAppointmentTime() + "</li>"
                    + "</ul>"
                    + "<p>Vui lòng đặt lại lịch hẹn nếu cần.</p>"
                    + "<br><p>Trân trọng,<br>Phòng khám VNVC</p>";

            emailService.sendAppointmentNotification(email, subject, body);
        }
    }

    public List<AppointmentDTO> getPastAppointments() {
        LocalDateTime now = LocalDateTime.now();
        List<Appointment> pastAppointments = appointmentRepository.findAll().stream()
                .filter(appointment -> LocalDateTime.of(appointment.getAppointmentDate(), appointment.getAppointmentTime()).isBefore(now))
                .filter(appointment -> appointment.getStatus() != AppStatus.COMPLETED && appointment.getStatus() != AppStatus.CANCELLED)
                .collect(Collectors.toList());
        return pastAppointments.stream()
                .map(appointment -> {
                    AppointmentDTO appointmentDTO = new AppointmentDTO(appointment);
                    String phoneNumber = appointment.getCustomer().getUser().getPhone();
                    String cusName = appointment.getCustomer().getUser().getFullName();
                    String childName = appointment.getChild().getFullName();
                    appointmentDTO.setPhoneNumber(phoneNumber);
                    appointmentDTO.setCustomerName(cusName);
                    appointmentDTO.setChildName(childName);
                    return appointmentDTO;
                })
                .collect(Collectors.toList());
    }

    public boolean rescheduleAppointment(String appointmentId, RescheduleAppointmentRequest request) {
        Optional<Appointment> optionalAppointment = appointmentRepository.findByAppId(appointmentId);

        if (optionalAppointment.isPresent()) {
            Appointment appointment = optionalAppointment.get();

            if (appointment.getStatus() == AppStatus.CANCELLED || appointment.getStatus() == AppStatus.COMPLETED) {
                return false; // Không thể thay đổi cuộc hẹn đã cancel hoặc đã complete
            }
            appointment.setAppointmentDate(request.getNewDate());
            appointment.setAppointmentTime(request.getNewTime());
            appointmentRepository.save(appointment);
            return true;
        }
        return false;
    }
}