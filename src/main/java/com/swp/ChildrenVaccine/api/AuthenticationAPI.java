package com.swp.ChildrenVaccine.api;

import com.swp.ChildrenVaccine.dto.request.LoginRequest;
import com.swp.ChildrenVaccine.dto.request.RegisterRequest;
import com.swp.ChildrenVaccine.entities.RevokedToken;
import com.swp.ChildrenVaccine.exception.EmailAlreadyExistsException;
import com.swp.ChildrenVaccine.repository.RevokedTokenRepository;
import com.swp.ChildrenVaccine.repository.UserRepository;
import com.swp.ChildrenVaccine.service.AuthenticationService;
import com.swp.ChildrenVaccine.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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

    @PostMapping("/login")
    public ResponseEntity<ResponseEntity<?>> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
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
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String token = tokenService.extractToken(request);
        if (token != null) {
            RevokedToken revokedToken = new RevokedToken();
            revokedToken.setToken(token);
            revokedToken.setRevokedAt(LocalDateTime.now());
            revokedTokenRepository.save(revokedToken);
        }
        return ResponseEntity.ok("Đăng xuất thành công!");
    }
}