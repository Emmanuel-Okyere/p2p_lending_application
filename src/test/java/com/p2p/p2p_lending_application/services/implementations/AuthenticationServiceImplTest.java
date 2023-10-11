package com.p2p.p2p_lending_application.services.implementations;

import com.p2p.p2p_lending_application.enums.ERole;
import com.p2p.p2p_lending_application.models.Role;
import com.p2p.p2p_lending_application.models.User;
import com.p2p.p2p_lending_application.dto.requestDTO.LoginRequest;
import com.p2p.p2p_lending_application.dto.requestDTO.RegisterRequest;
import com.p2p.p2p_lending_application.repositories.RoleRepository;
import com.p2p.p2p_lending_application.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class AuthenticationServiceImplTest {
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private JwtUtilsImpl jwtUtilsImpl;
    @MockBean
    private RoleRepository roleRepository;
    @Autowired
    private AuthenticationServiceImpl authenticationServiceImpl;
    @BeforeEach
    void setUp() {

    }

    @Test
    void loginUserWithActualExistentCredentials() {
        LoginRequest loginRequest = new LoginRequest("emmanuel@gmail.com", "password");
//        Mockito.doReturn(new Authentication())
//                .when(authenticationManager)
//                .authenticate(any());
        Mockito.doReturn("wqqwwsdsasas").when(jwtUtilsImpl).generateJwtToken(any());
        Mockito.verify(jwtUtilsImpl,Mockito.times(1)).generateJwtToken(any());
    }

    @Test
    void userAlreadyExistSoItShouldReturnABadRequestHttpStatus() {
        Optional<User> user = Optional.of(new User("Emmanuel",
                "Emmanuel Okyere Gyateng",
                "emmna@gmail.com",
                "password",
                "password"));
        RegisterRequest registerRequest = new RegisterRequest("Emmanuel",
                "Emmanuel Okyere Gyateng",
                "emmna@gmail.com",
                "ADMIN",
                "password",
                "password");
        Optional<Role> role = Optional.of(new Role(ERole.ADMIN));
        Mockito.doReturn(user).when(userRepository).findByemailAddress(any());
        Mockito.doReturn(role).when(roleRepository).findByName(any());
        ResponseEntity<?> response = authenticationServiceImpl.createUser(registerRequest);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }
    @Test
    void userEmailDoesNotExistButPasswordLengthIsLessThan8SoHttpBadRequestShouldBeReturned(){
        RegisterRequest registerRequest = new RegisterRequest("Emmanuel",
                "Emmanuel Okyere Gyateng",
                "emmna@gmail.com",
                "ADMIN",
                "passwo",
                "passwo");
        Optional<Role> role = Optional.of(new Role(ERole.ADMIN));
        Mockito.doReturn(role).when(roleRepository).findByName(any());
        Mockito.doReturn(Optional.empty()).when(userRepository).findByemailAddress(any());
        ResponseEntity<?> response = authenticationServiceImpl.createUser(registerRequest);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }
    @Test
    void userEmailDoesNotExistButPasswordAndConfirmPasswordDoNotMatchSoHttpBadRequestShouldBeReturned(){
        RegisterRequest registerRequest = new RegisterRequest("Emmanuel",
                "Emmanuel Okyere Gyateng",
                "emmna@gmail.com",
                "ADMIN",
                "password",
                "pass");
        Optional<Role> role = Optional.of(new Role(ERole.ADMIN));
        Mockito.doReturn(role).when(roleRepository).findByName(any());
        Mockito.doReturn(Optional.empty()).when(userRepository).findByemailAddress(any());
        ResponseEntity<?> response = authenticationServiceImpl.createUser(registerRequest);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }
    @Test
    void userEmailDoesNotExistAndUserRequestIsValidSoUserIsCreatedSuccessfully(){
        User user = new User("Emmanuel",
                "Emmanuel Okyere Gyateng",
                "emmna@gmail.com",
                "password",
                "password");
        RegisterRequest registerRequest = new RegisterRequest("Emmanuel",
                "Emmanuel Okyere Gyateng",
                "emmna@gmail.com",
                "ADMIN",
                "password",
                "password");
        Optional<Role> role = Optional.of(new Role(ERole.ADMIN));
        Mockito.doReturn(role).when(roleRepository).findByName(any());
        Mockito.doReturn(Optional.empty()).when(userRepository).findByemailAddress(any());
        Mockito.doReturn(user).when(userRepository).save(user);
        ResponseEntity<?> response = authenticationServiceImpl.createUser(registerRequest);
        assertEquals(response.getStatusCode() ,HttpStatus.CREATED);
    }
}