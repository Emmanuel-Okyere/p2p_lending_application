package com.p2p.p2p_lending_application.services.interfaces;

import com.p2p.p2p_lending_application.dto.requestDTO.LoginRequest;
import com.p2p.p2p_lending_application.dto.requestDTO.RegisterRequest;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    ResponseEntity<?> loginUser(LoginRequest loginRequest);
    ResponseEntity<?> createUser(RegisterRequest registerRequest);
}
