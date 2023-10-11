package com.p2p.p2p_lending_application.services.interfaces;

import com.p2p.p2p_lending_application.dto.requestDTO.ProfileDTO;
import com.p2p.p2p_lending_application.models.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface ProfileService {
    ResponseEntity<?> getUserProfile(HttpHeaders header);
    ResponseEntity<?> createUserProfile(ProfileDTO profileDTO, HttpHeaders header);
    Optional<User> getUserFromHeader(HttpHeaders headers);
}
