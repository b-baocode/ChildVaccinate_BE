package com.swp.ChildrenVaccine.service.user.impl;

import com.swp.ChildrenVaccine.dto.request.user.UserCreateRequest;
import com.swp.ChildrenVaccine.dto.request.user.UserLoginRequest;
import com.swp.ChildrenVaccine.dto.response.user.UserResponse;
import com.swp.ChildrenVaccine.entities.User;
import com.swp.ChildrenVaccine.enums.RoleEnum;
import com.swp.ChildrenVaccine.exception.AppException;
import com.swp.ChildrenVaccine.exception.ErrorCode;
import com.swp.ChildrenVaccine.mapper.UserMapper;
import com.swp.ChildrenVaccine.repository.UserRepository;
import com.swp.ChildrenVaccine.service.user.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    @Override
    public UserResponse login(UserLoginRequest userLoginRequest) {
        User user = userRepository.findByEmail(userLoginRequest.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        if (passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword())) {
            UserResponse userResponse = userMapper.toUserResponse(user);
            switch (user.getRole()) {
                case CUSTOMER:
                    //role là cus thi chuyen sang trang customer
                    userResponse.setRedirectUrl("/customer/home");
                    break;
                case STAFF:
                    //chuyen sang trang staff
                    userResponse.setRedirectUrl("/staff/home");
                    break;
                case ADMIN:
                    //chuyen sang trang admin
                    userResponse.setRedirectUrl("/admin/home");
                    break;
                default:
                    throw new AppException(ErrorCode.UNAUTHORIZED);
            }
            return userResponse;
        } else {
            throw new AppException(ErrorCode.WRONG_PASSWORD);
        }
    }

    @Override
    public void logout() {

    }

    @Override
    public User create(UserCreateRequest userCreateRequest) {
        if (userRepository.existsByEmail(userCreateRequest.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
        User user = userMapper.toUser(userCreateRequest);
        user.setPassword(passwordEncoder.encode(userCreateRequest.getPassword()));
        user.setRole(RoleEnum.CUSTOMER); // Set default role
        return userRepository.save(user);
    }

    @Override
    public UserResponse viewProfile() {
        var context = SecurityContextHolder.getContext();
        String email = context.getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }
}