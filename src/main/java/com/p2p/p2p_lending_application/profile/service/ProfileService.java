package com.p2p.p2p_lending_application.profile.service;

import com.p2p.p2p_lending_application.authentication.model.User;
import com.p2p.p2p_lending_application.authentication.repository.UserRepository;
import com.p2p.p2p_lending_application.authentication.security.jwt.JwtUtils;
import com.p2p.p2p_lending_application.profile.model.Telephone;
import com.p2p.p2p_lending_application.profile.model.UserProfile;
import com.p2p.p2p_lending_application.profile.payload.request.ProfileDTO;
import com.p2p.p2p_lending_application.profile.repository.NextOfKinRepository;
import com.p2p.p2p_lending_application.profile.repository.TelephoneRepository;
import com.p2p.p2p_lending_application.profile.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TelephoneRepository telephoneRepository;
    @Autowired
    private NextOfKinRepository nextOfKinRepository;

    public ResponseEntity<?> getUserProfile(HttpHeaders header) {
        String token  = Objects.requireNonNull(header.getFirst(HttpHeaders.AUTHORIZATION)).substring(7);
        String emailAddress = jwtUtils.getEmailFromJwtToken(token);
        Optional<User> user = userRepository.findByemailAddress(emailAddress);
        Optional<UserProfile> userProfile = userProfileRepository.findById(user.get().getId());
        if(userProfile.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(userProfile);
        }
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("status","failure","message","user has no profile"));
    }

    public ResponseEntity<?> createUserProfile(ProfileDTO profileDTO, HttpHeaders header) {
            String token  = Objects.requireNonNull(header.getFirst(HttpHeaders.AUTHORIZATION)).substring(7);
            String emailAddress = jwtUtils.getEmailFromJwtToken(token);
            Optional<User> user = userRepository.findByemailAddress(emailAddress);
            for(Telephone telephone : profileDTO.getTelephoneNumber()){
//                telephone.setProfileId();
                telephone.setVerified(false);
                telephoneRepository.save(telephone);
            }
            profileDTO.setUser(user.get());
        nextOfKinRepository.save(profileDTO.getNextOfKin());
        return ResponseEntity.status(HttpStatus.OK).body("");
    }
}
