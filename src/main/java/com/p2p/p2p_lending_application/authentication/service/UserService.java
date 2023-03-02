package com.p2p.p2p_lending_application.authentication.service;

import com.p2p.p2p_lending_application.authentication.exception.RoleNotFoundException;
import com.p2p.p2p_lending_application.authentication.model.ERole;
import com.p2p.p2p_lending_application.authentication.model.RefreshToken;
import com.p2p.p2p_lending_application.authentication.model.Role;
import com.p2p.p2p_lending_application.authentication.model.User;
import com.p2p.p2p_lending_application.authentication.payload.requestDTO.LoginRequest;
import com.p2p.p2p_lending_application.authentication.payload.requestDTO.RegisterRequest;
import com.p2p.p2p_lending_application.authentication.payload.responseDTO.UserResponses;
import com.p2p.p2p_lending_application.authentication.repository.RoleRepository;
import com.p2p.p2p_lending_application.authentication.repository.UserRepository;
import com.p2p.p2p_lending_application.authentication.security.jwt.JwtUtils;
import com.p2p.p2p_lending_application.profile.model.UserProfile;
import com.p2p.p2p_lending_application.profile.repository.UserProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private RefreshTokenService refreshTokenService;
    public JwtUtils jwtUtils;
    private UserProfileRepository userProfileRepository;

    public ResponseEntity<?> loginUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmailAddress(),loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
        List<String> role = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map
                        .of("status","success",
                                "message","login success",
                                "accessToken",jwt,
                                "refreshToken",refreshToken.getToken(),
                                "username",userDetails.getUsername(),
                                "emailAddress",userDetails.getEmail(),
                                "id",userDetails.getId(),
                                "role",role.get(0)));
    }

    public ResponseEntity<?> createUser(RegisterRequest registerRequest) throws RoleNotFoundException {
        User user = new User(registerRequest.getUsername(),registerRequest.getFullName(), registerRequest.getEmailAddress(),registerRequest.getPassword(),registerRequest.getConfirmPassword());
        Optional<User> user1 = userRepository.findByemailAddress(user.getEmailAddress());
        if (user1.isPresent()){
            return ResponseEntity.badRequest().body(new UserResponses("failure","email already taken"));
        }
        else if(!user.getPassword().equals(user.getConfirmPassword()) || !(user.getPassword().length()>=7)){
            return ResponseEntity.badRequest().body(new UserResponses("failure","password invalid"));
        }
        String strRoles = registerRequest.getRole();
        Set<Role> role = new HashSet<>();
        if (strRoles == null) {
            Role borrowerRole = roleRepository.findByName(ERole.BORROWER)
                    .orElseThrow(() -> new RoleNotFoundException(HttpStatus.OK,"role not found"));
            role.add(borrowerRole);
        } else {
                switch (strRoles.toLowerCase()) {
                    case "admin" -> {
                        Role adminRole = roleRepository.findByName(ERole.ADMIN)
                                .orElseThrow(() -> new RoleNotFoundException(HttpStatus.OK,"role not found"));
                        role.add(adminRole);
                    }
                    case "lender" ->{
                        Role lenderRole = roleRepository.findByName(ERole.LENDER)
                                .orElseThrow(() -> new RoleNotFoundException(HttpStatus.OK,"role not found"));
                        role.add(lenderRole);
                    }
                    default -> {
                        Role borrowerRole = roleRepository.findByName(ERole.BORROWER)
                                .orElseThrow(() -> new RoleNotFoundException(HttpStatus.OK,"role not found"));
                        role.add(borrowerRole);
                    }
                }
        }
        user.setApproved(false);
        user.setVerified(false);
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        createUserProfile(user.getEmailAddress());
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponses("success","user created"));
    }

    private void createUserProfile(String emailAddress) {
        Optional<User> user= userRepository.findByemailAddress(emailAddress);
        userProfileRepository.save(new UserProfile(user.get()));
    }
}
