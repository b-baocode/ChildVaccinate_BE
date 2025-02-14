package com.swp.ChildrenVaccine.service.user;

import com.swp.ChildrenVaccine.dto.request.user.UserCreateRequest;
import com.swp.ChildrenVaccine.dto.request.user.UserLoginRequest;
import com.swp.ChildrenVaccine.dto.response.user.UserResponse;
import com.swp.ChildrenVaccine.entities.User;

public interface UserService {
    public UserResponse login(UserLoginRequest userLoginRequest);
    public void logout();
    public User create(UserCreateRequest userCreateRequest);
    public UserResponse viewProfile();

}
