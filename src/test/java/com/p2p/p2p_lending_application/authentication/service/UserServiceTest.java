package com.p2p.p2p_lending_application.authentication.service;

import com.p2p.p2p_lending_application.authentication.model.User;
import com.p2p.p2p_lending_application.authentication.payload.requestDTO.LoginRequest;
import com.p2p.p2p_lending_application.authentication.repository.UserRepository;
import com.p2p.p2p_lending_application.authentication.security.jwt.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class UserServiceTest {
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private JwtUtils jwtUtils;
    @Autowired
    private UserService userService;
    @BeforeEach
    void setUp() {

    }

    @Test
    void loginUserWithActualExistentCredentials() {
        LoginRequest loginRequest = new LoginRequest("emmanuel@gmail.com", "password");
//        Mockito.doReturn(new Authentication())
//                .when(authenticationManager)
//                .authenticate(any());
        Mockito.doReturn("wqqwwsdsasas").when(jwtUtils).generateJwtToken(any());
        Mockito.verify(jwtUtils,Mockito.times(1)).generateJwtToken(any());
    }

    @Test
    void userAlreadyExistSoItShouldReturnABadRequestHttpStatus() {
        Optional<User> user = Optional.of(new User("Emmanuel",
                "Emmanuel Okyere Gyateng",
                "emmna@gmail.com",
                "password",
                "password"));
        Mockito.doReturn(user).when(userRepository).findByemailAddress(any());
        ResponseEntity<?> response = userService.createUser(user.get());
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }
    @Test
    void userEmailDoesNotExistButPasswordLengthIsLessThan8SoHttpBadRequestShouldBeReturned(){
        Optional<User> user = Optional.of(new User("Emmanuel",
                "Emmanuel Okyere Gyateng",
                "emmna@gmail.com",
                "passwo",
                "passwo"));
        Mockito.doReturn(Optional.empty()).when(userRepository).findByemailAddress(any());
        ResponseEntity<?> response = userService.createUser(user.get());
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }
    @Test
    void userEmailDoesNotExistButPasswordAndConfirmPasswordDoNotMatchSoHttpBadRequestShouldBeReturned(){
        Optional<User> user = Optional.of(new User("Emmanuel",
                "Emmanuel Okyere Gyateng",
                "emmna@gmail.com",
                "password",
                "pass"));
        Mockito.doReturn(Optional.empty()).when(userRepository).findByemailAddress(any());
        ResponseEntity<?> response = userService.createUser(user.get());
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }
    @Test
    void userEmailDoesNotExistAndUserRequestIsValidSoUserIsCreatedSuccessfully(){
        User user = new User("Emmanuel",
                "Emmanuel Okyere Gyateng",
                "emmna@gmail.com",
                "password",
                "password");
        Mockito.doReturn(Optional.empty()).when(userRepository).findByemailAddress(any());
        Mockito.doReturn(user).when(userRepository).save(user);
        ResponseEntity<?> response = userService.createUser(user);
        assertEquals(response.getStatusCode() ,HttpStatus.CREATED);
    }
}