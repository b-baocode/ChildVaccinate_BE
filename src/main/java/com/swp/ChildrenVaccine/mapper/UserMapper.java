package com.swp.ChildrenVaccine.mapper;

import com.swp.ChildrenVaccine.dto.request.user.UserCreateRequest;
import com.swp.ChildrenVaccine.dto.response.user.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.swp.ChildrenVaccine.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(User user);
    User toUser(UserCreateRequest userCreateRequest);
    UserResponse toUserResponse(User user);
}
