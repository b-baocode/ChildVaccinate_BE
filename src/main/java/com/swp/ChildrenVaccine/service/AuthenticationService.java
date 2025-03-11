package com.swp.ChildrenVaccine.service;

import com.swp.ChildrenVaccine.dto.request.LoginRequest;
import com.swp.ChildrenVaccine.dto.request.RegisterRequest;
import com.swp.ChildrenVaccine.entities.Customer;
import com.swp.ChildrenVaccine.entities.User;
import com.swp.ChildrenVaccine.enums.GenderEnum;
import com.swp.ChildrenVaccine.enums.RoleEnum;
import com.swp.ChildrenVaccine.exception.EmailAlreadyExistsException;
import com.swp.ChildrenVaccine.repository.CustomerRepository;
import com.swp.ChildrenVaccine.repository.UserRepository;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final TokenService jwtUtil;
    private final CustomerRepository customerRepository;
    private final UserService userService;
    private final CustomerService customerService;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(password)) {
                String token = jwtUtil.generateToken(user);
                Map<String, Object> response = new HashMap<>();
                response.put("token", token);
                Map<String, Object> userInfo = new HashMap<>();
                userInfo.put("id", user.getId());
                userInfo.put("email", user.getEmail());
                userInfo.put("fullName", user.getFullName());
                userInfo.put("role", user.getRole());
                userInfo.put("redirectUrl", getRedirectUrl(user.getRole()));
                response.put("user", userInfo);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("message", "Email hoặc mật khẩu không chính xác");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Email hoặc mật khẩu không chính xác");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }


    private String getRedirectUrl(RoleEnum role) {
        switch (role) {
            case CUSTOMER:
                return "/customer/home";
            case STAFF:
                return "/staff/home";
            case ADMIN:
                return "/admin/home";
            default:
                throw new IllegalArgumentException("Vai trò không hợp lệ");
        }
    }

    public void registerCustomer(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email đã tồn tại. Vui lòng nhập email khác!");
        }

        String userId = userService.generateUserId();
        String customerId = customerService.generateCustomerId();

        User user = new User();
        user.setId(userId);
        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());
        user.setPassword(request.getPassword());
        user.setPhone(request.getPhone());
        user.setRole(RoleEnum.CUSTOMER); // Mặc định là CUSTOMER
        user.setActive(true);

        Customer customer = new Customer();
        customer.setCusId(customerId);
        customer.setUser(user);
        customer.setAddress(request.getAddress());
        customer.setDateOfBirth(request.getDateOfBirth());
        customer.setGender(GenderEnum.valueOf(request.getGender()));

        userRepository.save(user);
        customerRepository.save(customer);
    }
}



