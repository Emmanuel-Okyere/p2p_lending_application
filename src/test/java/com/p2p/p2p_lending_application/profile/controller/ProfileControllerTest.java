package com.p2p.p2p_lending_application.profile.controller;

import com.p2p.p2p_lending_application.profile.payload.request.ProfileDTO;
import com.p2p.p2p_lending_application.profile.service.ProfileService;
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
    private ProfileService profileService;
    @Autowired
    private ProfileController profileController;
    @Mock
    private HttpHeaders headers;

    @Test
    void getUserProfile() {
        Mockito.doReturn(ResponseEntity.
                        status(HttpStatus.OK)
                        .body(Map.of("status","success","message","loan requested successfully")))
                .when(profileService).getUserProfile(headers);
        profileController.getUserProfile(headers);
        Mockito.verify(profileService, Mockito.times(1)).getUserProfile(any());
    }

    @Test
    void createUserProfile() {
        ProfileDTO request = new ProfileDTO();
        Mockito.doReturn(ResponseEntity.
                        status(HttpStatus.OK)
                        .body(Map.of("status","success","message","loan requested successfully")))
                .when(profileService).createUserProfile(request,headers);
        profileController.createUserProfile(request,headers);
        Mockito.verify(profileService, Mockito.times(1)).createUserProfile(any(),any());
    }
}