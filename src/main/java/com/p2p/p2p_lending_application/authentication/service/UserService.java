package com.p2p.p2p_lending_application.authentication.service;

import com.p2p.p2p_lending_application.authentication.model.RefreshToken;
import com.p2p.p2p_lending_application.authentication.model.User;
import com.p2p.p2p_lending_application.authentication.payload.requestDTO.LoginRequest;
import com.p2p.p2p_lending_application.authentication.payload.responseDTO.UserResponses;
import com.p2p.p2p_lending_application.authentication.repository.UserRepository;
import com.p2p.p2p_lending_application.authentication.security.jwt.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;

    private PasswordEncoder passwordEncoder;
    private RefreshTokenService refreshTokenService;
    public JwtUtils jwtUtils;
    public ResponseEntity<?> loginUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmailAddress(),loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map
                        .of("status","success",
                                "message","login success",
                                "accessToken",jwt,
                                "refreshToken",refreshToken.getToken(),
                                "username",userDetails.getUsername(),
                                "emailAddress",userDetails.getEmail(),
                                "id",userDetails.getId()));
    }

    public ResponseEntity<?> createUser(User user) {
        Optional<User> user1 = userRepository.findByemailAddress(user.getEmailAddress());
        if (user1.isPresent()){
            return ResponseEntity.badRequest().body(new UserResponses("failure","email already taken"));
        }
        else if(!user.getPassword().equals(user.getConfirmPassword()) || !(user.getPassword().length()>=7)){
            return ResponseEntity.badRequest().body(new UserResponses("failure","password invalid"));
        }
        user.setApproved(false);
        user.setVerified(false);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponses("success","user created"));
    }
}
