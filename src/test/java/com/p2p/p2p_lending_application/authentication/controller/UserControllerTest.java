package com.p2p.p2p_lending_application.authentication.controller;

import com.p2p.p2p_lending_application.authentication.model.RefreshToken;
import com.p2p.p2p_lending_application.authentication.model.User;
import com.p2p.p2p_lending_application.authentication.payload.requestDTO.LoginRequest;
import com.p2p.p2p_lending_application.authentication.payload.requestDTO.RefreshTokenRequest;
import com.p2p.p2p_lending_application.authentication.payload.requestDTO.RegisterRequest;
import com.p2p.p2p_lending_application.authentication.repository.RefreshTokenRepository;
import com.p2p.p2p_lending_application.authentication.service.RefreshTokenService;
import com.p2p.p2p_lending_application.authentication.service.UserService;
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
class UserControllerTest {
    @MockBean
    private UserService userService;
    @MockBean
    private RefreshTokenService refreshTokenService;
    @Autowired
    private UserController userController;
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
        Mockito.doReturn(ResponseEntity.status(HttpStatus.CREATED).body("user created")).when(userService).createUser(registerRequest);
        userController.registerUser(registerRequest);
        Mockito.verify(userService,Mockito.times(1)).createUser(any());
    }

    @Test
    void loggingInAUserAndVerifyingThatUserServiceGetCalled() {
        LoginRequest loginRequest = new LoginRequest("emmanuel@gmail.com", "password");
        Mockito.doReturn(ResponseEntity.status(HttpStatus.OK).body("login success")).when(userService).loginUser(loginRequest);
        userController.loginUser(loginRequest);
        Mockito.verify(userService, Mockito.times(1)).loginUser(any());
    }

    @Test
    void refreshTokenInvalidAndReturnForbidden(){
        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest("4e411dd4-bd1c-4df1-9d71-57ff273f9c13");
        Mockito.doReturn(Optional.of(new RefreshToken())).when(refreshTokenRepository).findBytoken(any());
        Mockito.doReturn(Optional.of(new RefreshToken())).when(refreshTokenService).findByToken(any());
        userController.refreshToken(refreshTokenRequest);
        Mockito.verify(refreshTokenService,Mockito.times(1)).findByToken(any());
    }
}