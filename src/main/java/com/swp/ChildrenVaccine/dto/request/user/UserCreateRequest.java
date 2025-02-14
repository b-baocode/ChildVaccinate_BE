package com.swp.ChildrenVaccine.dto.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreateRequest {
    @NotBlank(message = "Email cannot be blank!")
    @Email
    String email;

    @NotBlank(message = "Password cannot be blank!")
    String password;

    @NotBlank(message = "Full name cannot be blank!")
    String fullName;

    @NotBlank(message = "Phone cannot be blank!")
    @Pattern(regexp = "^0\\d{9}$", message = "Phone number must start with 0 and be exactly 10 digits")
    String phone;
}
