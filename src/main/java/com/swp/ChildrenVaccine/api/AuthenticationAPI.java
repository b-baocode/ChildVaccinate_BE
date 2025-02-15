package com.swp.ChildrenVaccine.api;

import com.swp.ChildrenVaccine.dto.request.AuthenticationRequest;
import com.swp.ChildrenVaccine.dto.request.LogoutRequest;
import com.swp.ChildrenVaccine.dto.response.AuthenticationResponse;
import com.swp.ChildrenVaccine.service.authentication.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationAPI {
}
