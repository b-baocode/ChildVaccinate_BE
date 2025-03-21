package com.swp.ChildrenVaccine.api;

import com.swp.ChildrenVaccine.dto.request.LoginRequest;
import com.swp.ChildrenVaccine.dto.request.RegisterRequest;
import com.swp.ChildrenVaccine.entities.Customer;
import com.swp.ChildrenVaccine.entities.RevokedToken;
import com.swp.ChildrenVaccine.entities.Staff;
import com.swp.ChildrenVaccine.entities.User;
import com.swp.ChildrenVaccine.exception.EmailAlreadyExistsException;
import com.swp.ChildrenVaccine.repository.RevokedTokenRepository;
import com.swp.ChildrenVaccine.repository.UserRepository;
import com.swp.ChildrenVaccine.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationAPI {
    AuthenticationService authService;
    UserRepository userRepository;
    @Autowired
    private RevokedTokenRepository revokedTokenRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private final CustomerService customerService;
    @Autowired
    private OtpService otpService;
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;

//    @Autowired
//    private final StaffService staffService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpSession session) {
        // Call authService to authenticate user
        ResponseEntity<?> response = authService.login(request);
        Customer customer = customerService.findByEmail(request.getEmail());

        // Extract email from request and store it in session
        if (customer != null) {
            // Lưu toàn bộ thông tin Customer vào session
            session.setAttribute("loggedInCustomer", customer);
        }


        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerCustomer(@RequestBody RegisterRequest request) {
        try {
            authService.registerCustomer(request);
            return ResponseEntity.ok("Đăng ký thành công!");
        } catch (EmailAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpSession session) {
        String token = tokenService.extractToken(request);
        if (token != null) {
            RevokedToken revokedToken = new RevokedToken();
            revokedToken.setToken(token);
            revokedToken.setRevokedAt(LocalDateTime.now());
            revokedTokenRepository.save(revokedToken);
        }
        session.invalidate();
        return ResponseEntity.ok("Đăng xuất thành công!");
    }

    @GetMapping("/session-info")
    public ResponseEntity<?> getSessionInfo(HttpSession session) {
        Customer customer = (Customer) session.getAttribute("loggedInCustomer");

        if (customer == null) {
            return ResponseEntity.badRequest().body("Không tìm thấy thông tin khách hàng trong session.");
        }

        return ResponseEntity.ok(customer);
    }

    @GetMapping("/staff/session-info")
    public ResponseEntity<?> getStaffSessionInfo(HttpSession session) {
        Staff staff = (Staff) session.getAttribute("loggedInStaff");

        if (staff == null) {
            return ResponseEntity.badRequest().body("Không tìm thấy thông tin staff trong session.");
        }

        return ResponseEntity.ok(staff);
    }

    @PostMapping("/request-otp")
    public ResponseEntity<String> requestOtp(@RequestParam String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email không tồn tại!");
        }

        String otp = otpService.generateOtp();
        otpService.saveOtp(email, otp);
        emailService.sendOtpEmail(email, otp);

        return ResponseEntity.ok("OTP đã được gửi đến email!");
    }

    @PostMapping("/validate-otp")
    public ResponseEntity<String> validateOtp(@RequestParam String email, @RequestParam String otp) {
        if (otpService.validateOtp(email, otp)) {
            return ResponseEntity.ok("OTP hợp lệ, bạn có thể đặt lại mật khẩu.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("OTP không hợp lệ hoặc đã hết hạn.");
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String email,
                                                @RequestParam String otp,
                                                @RequestParam String newPassword) {
        if (!otpService.validateOtp(email, otp)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("OTP không hợp lệ hoặc đã hết hạn.");
        }

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();

            existingUser.setPassword(newPassword); // Không mã hóa mật khẩu

            userService.saveNewUserPassword(existingUser);

            return ResponseEntity.ok("Mật khẩu đã được đặt lại thành công!");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email không tồn tại!");
    }
}