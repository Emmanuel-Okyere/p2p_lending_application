package com.p2p.p2p_lending_application.authentication.controller;

import com.p2p.p2p_lending_application.authentication.model.User;
import com.p2p.p2p_lending_application.authentication.payload.requestDTO.LoginRequest;
import com.p2p.p2p_lending_application.authentication.payload.requestDTO.RegisterRequest;
import com.p2p.p2p_lending_application.authentication.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth/")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid RegisterRequest registerRequest){
        User user = new User(registerRequest.getUsername(),registerRequest.getFullName(), registerRequest.getEmailAddress(),registerRequest.getPassword(),registerRequest.getConfirmPassword());
        return userService.createUser(user);
    }

    @PostMapping("login")
    public ResponseEntity<?> loginUser(@RequestBody @Valid LoginRequest loginRequest){
        return userService.loginUser(loginRequest);

    }
}
