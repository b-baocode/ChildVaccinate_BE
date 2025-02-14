package com.swp.ChildrenVaccine.service.authentication;

import com.nimbusds.jose.JOSEException;
import com.swp.ChildrenVaccine.dto.request.AuthenticationRequest;
import com.swp.ChildrenVaccine.dto.request.IntrospectRequest;
import com.swp.ChildrenVaccine.dto.request.LogoutRequest;
import com.swp.ChildrenVaccine.dto.response.AuthenticationResponse;
import com.swp.ChildrenVaccine.dto.response.IntrospectResponse;

import java.text.ParseException;

public interface AuthenticationService {
    AuthenticationResponse  ahthenticated(AuthenticationRequest authenticationRequest);
    IntrospectResponse introspect(IntrospectRequest introspectRequest) throws JOSEException, ParseException;
    public void logout(LogoutRequest logoutRequest) throws JOSEException, ParseException;

}
