package com.swp.ChildrenVaccine.dto.request;

import com.swp.ChildrenVaccine.entities.User;
import com.swp.ChildrenVaccine.enums.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO {
    Long id;
    String email;
    String password;
    String fullName;
    String phone;
    Role role;
    Boolean active;

    public UserDTO (User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.fullName = user.getFullName();
        this.phone = user.getPhone();
        this.role = user.getRole();
        this.active = user.isActive();
    }

    public static UserDTO fromUser(User user) {
        if(user == null) {
            return null;
        } else {
            return new UserDTO (
                    user.getId(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getFullName(),
                    user.getPhone(),
                    user.getRole(),
                    user.isActive()
            );
        }
    }
}

