package com.p2p.p2p_lending_application.profile.service;

import com.p2p.p2p_lending_application.authentication.model.User;
import com.p2p.p2p_lending_application.authentication.repository.UserRepository;
import com.p2p.p2p_lending_application.authentication.security.jwt.JwtUtils;
import com.p2p.p2p_lending_application.profile.model.NextOfKin;
import com.p2p.p2p_lending_application.profile.model.Telephone;
import com.p2p.p2p_lending_application.profile.model.UserProfile;
import com.p2p.p2p_lending_application.profile.payload.request.ProfileDTO;
import com.p2p.p2p_lending_application.profile.payload.response.UserProfileResponse;
import com.p2p.p2p_lending_application.profile.repository.NextOfKinRepository;
import com.p2p.p2p_lending_application.profile.repository.TelephoneRepository;
import com.p2p.p2p_lending_application.profile.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
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
        Optional<UserProfile> userProfile = userProfileRepository.findByUserId(user.get().getId());
        if(userProfile.isPresent()){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new UserProfileResponse("success",
                            "profile fetch successful", userProfile.get()));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("status","failure","message","user has no profile"));
    }

    public ResponseEntity<?> createUserProfile(ProfileDTO profileDTO, HttpHeaders header) {
        String token  = Objects.requireNonNull(header.getFirst(HttpHeaders.AUTHORIZATION)).substring(7);
        String emailAddress = jwtUtils.getEmailFromJwtToken(token);
        Optional<User> user = userRepository.findByemailAddress(emailAddress);
        Optional<UserProfile> userProfile = userProfileRepository.findByUserId(user.get().getId());
        Optional<NextOfKin> nextOfKin = nextOfKinRepository.findNextOfKinByuserProfile(userProfile.get());
        List<String> telephones = telephoneRepository.findAllByProfileId(userProfile.get())
                .stream().map(Telephone::getNumber).toList();
        for(Telephone telephone : profileDTO.getTelephoneNumber()){
            if(!telephones.contains(telephone.getNumber())){
                telephone.setProfileId(userProfile.get());
                telephone.setVerified(false);
                telephoneRepository.save(telephone);
            }
        }
        if(nextOfKin.isEmpty()){
            NextOfKin nextOfKin1 = new NextOfKin(
                    userProfile.get(),
                    profileDTO.getNextOfKin().getFullName(),
                    profileDTO.getNextOfKin().getEmailAddress());
            NextOfKin news = nextOfKinRepository.save(nextOfKin1);
            userProfile.get().setNextOfKin(news);
        }
        else{
            nextOfKin.get().setEmailAddress(profileDTO.getNextOfKin().getEmailAddress());
            nextOfKin.get().setFullName(profileDTO.getNextOfKin().getFullName());
            nextOfKinRepository.save(nextOfKin.get());
        }
        userProfile.get().setDigitalAddress(profileDTO.getDigitalAddress());
        userProfile.get().setDateOfBirth(profileDTO.getDateOfBirth());
        UserProfile userProfile1 = userProfileRepository.save(userProfile.get());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new UserProfileResponse("success",
                "profile saved successfully", userProfile1));
    }
//    public ResponseEntity<?> updateUserProfile(UserProfile userProfile, Long portfolioID) {
//        Optional<UserProfile> userProfile1 = userProfileRepository.findById(portfolioID);
//        userProfileRepository.save(userProfile1.get());
//    }
}
