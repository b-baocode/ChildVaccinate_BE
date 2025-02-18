package com.swp.ChildrenVaccine.api;

import com.swp.ChildrenVaccine.dto.request.LoginRequest;
import com.swp.ChildrenVaccine.repository.UserRepository;
import com.swp.ChildrenVaccine.service.AuthenticationService;
import com.swp.ChildrenVaccine.service.TokenService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationAPI {
    AuthenticationService authService;
    UserRepository userRepository;
    TokenService jwtUtil;
    @PostMapping("/login")
    public ResponseEntity<ResponseEntity<?>> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
