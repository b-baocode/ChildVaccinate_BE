package com.swp.ChildrenVaccine.dto.response.user;

import com.swp.ChildrenVaccine.enums.RoleEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    Long id;
    String email;
    String password;
    String fullName;
    String phone;
    RoleEnum role;
    Boolean active;
    String redirectUrl;
}
