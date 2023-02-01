package com.p2p.p2p_lending_application.authentication.controller;

import com.p2p.p2p_lending_application.authentication.exception.TokenRefreshException;
import com.p2p.p2p_lending_application.authentication.model.RefreshToken;
import com.p2p.p2p_lending_application.authentication.model.User;
import com.p2p.p2p_lending_application.authentication.payload.requestDTO.LoginRequest;
import com.p2p.p2p_lending_application.authentication.payload.requestDTO.RefreshTokenRequest;
import com.p2p.p2p_lending_application.authentication.payload.requestDTO.RegisterRequest;
import com.p2p.p2p_lending_application.authentication.payload.responseDTO.TokenRefreshResponse;
import com.p2p.p2p_lending_application.authentication.service.RefreshTokenService;
import com.p2p.p2p_lending_application.authentication.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/auth/")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RefreshTokenService refreshTokenService;
    
    @PostMapping("register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid RegisterRequest registerRequest){
        User user = new User(registerRequest.getUsername(),registerRequest.getFullName(), registerRequest.getEmailAddress(),registerRequest.getPassword(),registerRequest.getConfirmPassword());
        return userService.createUser(user);
    }

    @PostMapping("login")
    public ResponseEntity<?> loginUser(@RequestBody @Valid LoginRequest loginRequest){
        return userService.loginUser(loginRequest);

    }
    @PostMapping("refreshtoken")
    public ResponseEntity<?> refreshToken(@RequestBody @Valid RefreshTokenRequest refreshTokenRequest){
        return refreshTokenService.findByToken(refreshTokenRequest.getRefreshToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = userService.jwtUtils.generateTokenFromEmailAddress(user.getEmailAddress());
                    return ResponseEntity.status(HttpStatus.OK).body(new TokenRefreshResponse(token,refreshTokenRequest.getRefreshToken()));
                }).orElseThrow(() -> new TokenRefreshException(refreshTokenRequest.getRefreshToken(),"refresh token is not valid"));
    }
}
