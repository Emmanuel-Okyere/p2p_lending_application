package com.p2p.p2p_lending_application.controllers;

import com.p2p.p2p_lending_application.dto.requestDTO.ProfileDTO;
import com.p2p.p2p_lending_application.services.implementations.ProfileServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class ProfileControllerTest {
    @MockBean
    private ProfileServiceImpl profileServiceImpl;
    @Autowired
    private ProfileController profileController;
    @Mock
    private HttpHeaders headers;

    @Test
    void getUserProfile() {
        Mockito.doReturn(ResponseEntity.
                        status(HttpStatus.OK)
                        .body(Map.of("status","success","message","loan requested successfully")))
                .when(profileServiceImpl).getUserProfile(headers);
        profileController.getUserProfile(headers);
        Mockito.verify(profileServiceImpl, Mockito.times(1)).getUserProfile(any());
    }

    @Test
    void createUserProfile() {
        ProfileDTO request = new ProfileDTO();
        Mockito.doReturn(ResponseEntity.
                        status(HttpStatus.OK)
                        .body(Map.of("status","success","message","loan requested successfully")))
                .when(profileServiceImpl).createUserProfile(request,headers);
        profileController.createUserProfile(request,headers);
        Mockito.verify(profileServiceImpl, Mockito.times(1)).createUserProfile(any(),any());
    }
}