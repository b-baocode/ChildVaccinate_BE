package com.swp.ChildrenVaccine.controller;

import com.swp.ChildrenVaccine.entities.Appointment;
import com.swp.ChildrenVaccine.enums.AppStatus;
import com.swp.ChildrenVaccine.enums.PaymentStatus;
import com.swp.ChildrenVaccine.repository.AppointmentRepository;
import com.swp.ChildrenVaccine.service.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/payment")
public class VNPayController {
    @Autowired
    private VNPayService vnPayService;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @PostMapping("/create/{appointmentId}")
    public ResponseEntity<Map<String, String>> createPayment(@PathVariable String appointmentId, HttpServletRequest request) {
        Optional<Appointment> appointmentOpt = appointmentRepository.findById(appointmentId);
        if (!appointmentOpt.isPresent()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "AppointmentId is not correct");
            return ResponseEntity.badRequest().body(error);
        }

        Appointment appointment = appointmentOpt.get();
        if (appointment.getPaymentStatus() == PaymentStatus.PAID) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Appointment " + appointment.getAppId() + " đã thanh toán rồi");
            return ResponseEntity.ok(response);
        }

        // tao url vnpay
        String baseUrl = request.getScheme() + "://" + request.getServerName();
        if (request.getServerPort() != 80 && request.getServerPort() != 443) {
            baseUrl += ":" + request.getServerPort();
        }

        baseUrl = baseUrl.replaceAll("[\\n\\r]", "").replace("%0A", "").trim();

        String vnPayUrl = vnPayService.createPayment(appointment, request, baseUrl);

        Map<String, String> response = new HashMap<>();
        response.put("vnpayUrl", vnPayUrl);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/vnpay-return")
    public ResponseEntity<Object> vnpayReturn(HttpServletRequest request, HttpServletResponse response) {
        try {
            vnPayService.processPaymentResponse(request, response);

            String appointmentId = request.getParameter("vnp_OrderInfo");
            String paymentTime = request.getParameter("vnp_PayDate");
            String transactionId = request.getParameter("vnp_TransactionNo");
            String totalPrice = request.getParameter("vnp_Amount");

            if(appointmentId == null || paymentTime == null || transactionId == null || totalPrice == null) {
                return ResponseEntity.badRequest().body("Missing parameters");
            }

            System.out.println("Appointment ID: " + appointmentId);
            System.out.println("Payment time: " + paymentTime);
            System.out.println("Transaction ID: " + transactionId);
            System.out.println("Total price: " + totalPrice);

            Appointment appointment = appointmentRepository.findByAppId(appointmentId)
                    .orElseThrow(() -> new RuntimeException("Appointment not found"));

            if("00".equals(request.getParameter("vnp_TransactionStatus"))) {
                appointment.setPaymentStatus(PaymentStatus.PAID);
            } else {
                appointment.setPaymentStatus(PaymentStatus.PENDING);
            }
            appointmentRepository.save(appointment);

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("appointmentId", appointmentId);
            responseData.put("paymentTime", paymentTime);
            responseData.put("transactionId", transactionId);
            responseData.put("totalPrice", totalPrice);

            return ResponseEntity.ok().body(responseData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }
}
//String vnp_TxnRef = vnp_Params.get("vnp_TxnRef");
//String vnp_TransactionStatus = vnp_Params.get("vnp_TransactionStatus");
//
//Appointment appointment = appointmentRepository.findById(vnp_TxnRef)
//        .orElseThrow(() -> new RuntimeException("Appointment not found"));
//
//        if ("00".equals(vnp_TransactionStatus)) {
//        appointment.setPaymentStatus(PaymentStatus.PAID);
//            appointment.setStatus(AppStatus.CONFIRMED);
//        } else {
//                appointment.setPaymentStatus(PaymentStatus.FAIL); // Sửa FAIL thành FAILED (theo enum của bạn)
//        }
//
//                appointmentRepository.save(appointment);