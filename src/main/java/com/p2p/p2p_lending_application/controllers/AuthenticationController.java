package com.p2p.p2p_lending_application.controllers;

import com.p2p.p2p_lending_application.exceptions.TokenRefreshException;
import com.p2p.p2p_lending_application.models.RefreshToken;
import com.p2p.p2p_lending_application.dto.requestDTO.LoginRequest;
import com.p2p.p2p_lending_application.dto.requestDTO.RefreshTokenRequest;
import com.p2p.p2p_lending_application.dto.requestDTO.RegisterRequest;
import com.p2p.p2p_lending_application.dto.responseDTO.TokenRefreshResponse;
import com.p2p.p2p_lending_application.services.implementations.RefreshTokenServiceImpl;
import com.p2p.p2p_lending_application.services.implementations.AuthenticationServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/auth/")
public class AuthenticationController {
    @Autowired
    private AuthenticationServiceImpl authenticationServiceImpl;
    @Autowired
    private RefreshTokenServiceImpl refreshTokenServiceImpl;
    
    @PostMapping("register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid RegisterRequest registerRequest){
        return authenticationServiceImpl.createUser(registerRequest);
    }

    @PostMapping("login")
    public ResponseEntity<?> loginUser(@RequestBody @Valid LoginRequest loginRequest){
        return authenticationServiceImpl.loginUser(loginRequest);

    }
    @PostMapping("refreshtoken")
    public ResponseEntity<?> refreshToken(@RequestBody @Valid RefreshTokenRequest refreshTokenRequest){
        return refreshTokenServiceImpl.findByToken(refreshTokenRequest.getRefreshToken())
                .map(refreshTokenServiceImpl::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = authenticationServiceImpl.jwtUtilsImpl.generateTokenFromEmailAddress(user.getEmailAddress());
                    return ResponseEntity.status(HttpStatus.OK).body(new TokenRefreshResponse(token,refreshTokenRequest.getRefreshToken()));
                }).orElseThrow(() -> new TokenRefreshException(refreshTokenRequest.getRefreshToken(),"refresh token is not valid"));
    }
}
