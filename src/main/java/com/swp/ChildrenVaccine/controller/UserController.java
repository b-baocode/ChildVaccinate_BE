package com.swp.ChildrenVaccine.controller;

import com.swp.ChildrenVaccine.dto.request.user.UserCreateRequest;
import com.swp.ChildrenVaccine.dto.request.user.UserLoginRequest;
import com.swp.ChildrenVaccine.dto.response.ApiResponse;
import com.swp.ChildrenVaccine.dto.response.user.UserResponse;
import com.swp.ChildrenVaccine.entities.Customer;
import com.swp.ChildrenVaccine.entities.User;
import com.swp.ChildrenVaccine.exception.AppException;
import com.swp.ChildrenVaccine.repository.CustomerRepository;
import com.swp.ChildrenVaccine.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    @ExceptionHandler(AppException.class)
    ApiResponse<UserResponse> login(@RequestBody @Valid UserLoginRequest userLoginRequest) {
        UserResponse userResponse = userService.login(userLoginRequest);
        return ApiResponse.<UserResponse>builder()
                .status(200)
                .message("Login successful")
                .result(userResponse)
                .build();
    }

    @PostMapping("/register")
    ApiResponse<User> register(@RequestBody @Valid UserCreateRequest userCreateRequest) {
        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.create(userCreateRequest));
        return apiResponse;
    }

    @GetMapping("/profile")
    ApiResponse<UserResponse> getProfile(){
        return ApiResponse.<UserResponse>builder()
                .status(200)
                .message("Profile retrieved successfully")
                .result(userService.viewProfile())
                .build();
    }
}
