package com.p2p.p2p_lending_application.controllers;

import com.p2p.p2p_lending_application.models.RefreshToken;
import com.p2p.p2p_lending_application.dto.requestDTO.LoginRequest;
import com.p2p.p2p_lending_application.dto.requestDTO.RefreshTokenRequest;
import com.p2p.p2p_lending_application.dto.requestDTO.RegisterRequest;
import com.p2p.p2p_lending_application.repositories.RefreshTokenRepository;
import com.p2p.p2p_lending_application.services.implementations.RefreshTokenServiceImpl;
import com.p2p.p2p_lending_application.services.implementations.AuthenticationServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class AuthenticationControllerTest {
    @MockBean
    private AuthenticationServiceImpl authenticationServiceImpl;
    @MockBean
    private RefreshTokenServiceImpl refreshTokenServiceImpl;
    @Autowired
    private AuthenticationController authenticationController;
    @MockBean
    private RefreshTokenRepository refreshTokenRepository;
    @Test
    void registeringAUserAndVerifyingThatUserServiceGetCalled() {
        RegisterRequest registerRequest = new RegisterRequest(
                "Emmanuel",
                "Emmanuel Okyere Gyateng",
                "emmna@gmail.com",
                "ADMIN",
                "password",
                "password");
        Mockito.doReturn(ResponseEntity.status(HttpStatus.CREATED).body("user created")).when(authenticationServiceImpl).createUser(registerRequest);
        authenticationController.registerUser(registerRequest);
        Mockito.verify(authenticationServiceImpl,Mockito.times(1)).createUser(any());
    }

    @Test
    void loggingInAUserAndVerifyingThatUserServiceGetCalled() {
        LoginRequest loginRequest = new LoginRequest("emmanuel@gmail.com", "password");
        Mockito.doReturn(ResponseEntity.status(HttpStatus.OK).body("login success")).when(authenticationServiceImpl).loginUser(loginRequest);
        authenticationController.loginUser(loginRequest);
        Mockito.verify(authenticationServiceImpl, Mockito.times(1)).loginUser(any());
    }

    @Test
    void refreshTokenInvalidAndReturnForbidden(){
        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest("4e411dd4-bd1c-4df1-9d71-57ff273f9c13");
        Mockito.doReturn(Optional.of(new RefreshToken())).when(refreshTokenRepository).findBytoken(any());
        Mockito.doReturn(Optional.of(new RefreshToken())).when(refreshTokenServiceImpl).findByToken(any());
        authenticationController.refreshToken(refreshTokenRequest);
        Mockito.verify(refreshTokenServiceImpl,Mockito.times(1)).findByToken(any());
    }
}