package com.swp.ChildrenVaccine.controller;

import com.swp.ChildrenVaccine.dto.request.PaymentRequest;
import com.swp.ChildrenVaccine.entities.Appointment;
import com.swp.ChildrenVaccine.entities.Schedule;
import com.swp.ChildrenVaccine.enums.PaymentStatus;
import com.swp.ChildrenVaccine.repository.AppointmentRepository;
import com.swp.ChildrenVaccine.repository.ScheduleRepository;
import com.swp.ChildrenVaccine.service.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/payment")
public class VNPayController {
    @Autowired
    private VNPayService vnPayService;

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;

    @PostMapping("/create-payment")
    public ResponseEntity<Map<String, Object>> createPayment(
            @RequestBody PaymentRequest paymentRequest,
            HttpServletRequest request) {

        String scheduleId = paymentRequest.getScheduleId();
        String appointmentId = paymentRequest.getAppointmentId();
        boolean payFull = paymentRequest.isPayFull();

        // Kiểm tra Schedule có tồn tại không
        Optional<Schedule> scheduleOpt = scheduleRepository.findById(scheduleId);
        if (!scheduleOpt.isPresent()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Schedule not found"));
        }
        Schedule schedule = scheduleOpt.get();

        // Lấy danh sách tất cả các Appointment thuộc Schedule
        List<Appointment> appointments = appointmentRepository.findBySchedule(schedule);

        // Lọc các appointment có trạng thái "PENDING"
        List<String> pendingAppointments = appointments.stream()
                .filter(a -> "PENDING".equalsIgnoreCase(String.valueOf(a.getPaymentStatus())))
                .map(Appointment::getAppId)
                .collect(Collectors.toList());

        // Nếu tất cả các appointment đã thanh toán, thông báo schedule đã thanh toán
        // hết
        if (pendingAppointments.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("error", "Schedule này đã thanh toán hết"));
        }

        // Nếu không thanh toán toàn bộ (payFull = false) => Kiểm tra appointmentId hợp
        // lệ không
        if (!payFull && appointmentId != null) {
            Optional<Appointment> appointmentOpt = appointmentRepository.findById(appointmentId);
            if (!appointmentOpt.isPresent() || !appointmentOpt.get().getSchedule().equals(schedule)) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Invalid appointment"));
            }
        }

        // Tạo URL return cho VNPay
        String baseUrl = request.getScheme() + "://" + request.getServerName();
        if (request.getServerPort() != 80 && request.getServerPort() != 443) {
            baseUrl += ":" + request.getServerPort();
        }
        baseUrl = baseUrl.replaceAll("[\\n\\r]", "").replace("%0A", "").trim();

        // Gọi service tạo link thanh toán
        String vnPayUrl = vnPayService.createPayment(schedule, payFull, appointmentId, request, baseUrl);
        // Trả về URL thanh toán và danh sách appointment chưa thanh toán
        Map<String, Object> response = new HashMap<>();
        response.put("vnpayUrl", vnPayUrl);
        response.put("pendingAppointments", pendingAppointments);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/vnpay-return")
    public ResponseEntity<Object> vnpayReturn(HttpServletRequest request, HttpServletResponse response) {
        try {
            vnPayService.processPaymentResponse(request, response);

            String orderInfo = request.getParameter("vnp_OrderInfo"); // VD: "SCHE001|APP:APP001"
            String paymentTime = request.getParameter("vnp_PayDate");
            String transactionId = request.getParameter("vnp_TransactionNo");
            String totalPrice = request.getParameter("vnp_Amount");
            String transactionStatus = request.getParameter("vnp_TransactionStatus");

            // Lấy payFull từ query parameter
            String payFullParam = request.getParameter("payFull");
            if (payFullParam == null) {
                return ResponseEntity.badRequest().body("Missing payFull parameter");
            }

            // Chuyển payFull từ String sang boolean
            boolean payFull = Boolean.parseBoolean(payFullParam);

            if (orderInfo == null || paymentTime == null || transactionId == null || totalPrice == null) {
                return ResponseEntity.badRequest().body("Missing parameters");
            }

            // Tách scheduleId và appointmentId từ orderInfo
            String scheduleId = orderInfo.split("\\|APP:")[0]; // VD: "SCHE001"
            String appointmentId = orderInfo.contains("|APP:") ? orderInfo.split("\\|APP:")[1] : null; // VD: "APP001"

            boolean isSuccessful = "00".equals(transactionStatus); // Kiểm tra giao dịch có thành công không

            System.out.println("payFull: " + payFull);
            System.out.println("appointmentId: " + appointmentId);

            // Tìm Schedule theo ID
            Schedule schedule = scheduleRepository.findById(scheduleId)
                    .orElseThrow(() -> new RuntimeException("Schedule not found"));

            if (isSuccessful) {
                if (payFull) {
                    // Nếu thanh toán toàn bộ, cập nhật tất cả appointment trong schedule thành PAID
                    List<Appointment> appointments = appointmentRepository.findBySchedule(schedule);
                    appointments.forEach(appointment -> appointment.setPaymentStatus(PaymentStatus.PAID));
                    appointmentRepository.saveAll(appointments);
                } else if (appointmentId != null) {
                    // Nếu chỉ thanh toán 1 appointment, cập nhật trạng thái cho appointment đó
                    Optional<Appointment> appointmentOpt = appointmentRepository.findById(appointmentId);
                    if (appointmentOpt.isPresent()) {
                        Appointment appointment = appointmentOpt.get();
                        if (appointment.getSchedule().equals(schedule)) {
                            appointment.setPaymentStatus(PaymentStatus.PAID);
                            appointmentRepository.save(appointment);
                        } else {
                            return ResponseEntity.badRequest().body("Invalid appointment for this schedule");
                        }
                    } else {
                        return ResponseEntity.badRequest().body("Appointment not found");
                    }
                }
            }

            // Chuẩn bị dữ liệu phản hồi
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("scheduleId", scheduleId);
            responseData.put("appointmentId", appointmentId);
            responseData.put("paymentTime", paymentTime);
            responseData.put("transactionId", transactionId);
            responseData.put("totalPrice", totalPrice);
            responseData.put("status", isSuccessful ? "PAID" : "PENDING");
            responseData.put("payFull", payFull); // Thêm payFull vào response để kiểm tra

            return ResponseEntity.ok().body(responseData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
// String vnp_TxnRef = vnp_Params.get("vnp_TxnRef");
// String vnp_TransactionStatus = vnp_Params.get("vnp_TransactionStatus");
//
// Appointment appointment = appointmentRepository.findById(vnp_TxnRef)
// .orElseThrow(() -> new RuntimeException("Appointment not found"));
//
// if ("00".equals(vnp_TransactionStatus)) {
// appointment.setPaymentStatus(PaymentStatus.PAID);
// appointment.setStatus(AppStatus.CONFIRMED);
// } else {
// appointment.setPaymentStatus(PaymentStatus.FAIL); // Sửa FAIL thành FAILED
// (theo enum của bạn)
// }
//
// appointmentRepository.save(appointment);