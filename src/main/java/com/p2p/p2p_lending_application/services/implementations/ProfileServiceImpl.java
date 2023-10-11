package com.p2p.p2p_lending_application.services.implementations;

import com.p2p.p2p_lending_application.models.User;
import com.p2p.p2p_lending_application.repositories.UserRepository;
import com.p2p.p2p_lending_application.models.NextOfKin;
import com.p2p.p2p_lending_application.models.Telephone;
import com.p2p.p2p_lending_application.models.UserProfile;
import com.p2p.p2p_lending_application.dto.requestDTO.ProfileDTO;
import com.p2p.p2p_lending_application.dto.responseDTO.UserProfileResponse;
import com.p2p.p2p_lending_application.repositories.NextOfKinRepository;
import com.p2p.p2p_lending_application.repositories.TelephoneRepository;
import com.p2p.p2p_lending_application.repositories.UserProfileRepository;
import com.p2p.p2p_lending_application.services.interfaces.ProfileService;
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
public class ProfileServiceImpl implements ProfileService {
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private JwtUtilsImpl jwtUtilsImpl;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TelephoneRepository telephoneRepository;
    @Autowired
    private NextOfKinRepository nextOfKinRepository;

    public ResponseEntity<?> getUserProfile(HttpHeaders header) {
        Optional<User> user = getUserFromHeader(header);
        Optional<UserProfile> userProfile = userProfileRepository.findByUserId(user.get().getId());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new UserProfileResponse("success",
                        "profile fetch successful", userProfile.get()));
    }

    public ResponseEntity<?> createUserProfile(ProfileDTO profileDTO, HttpHeaders header) {
        Optional<User> user = getUserFromHeader(header);
        Optional<UserProfile> userProfile = userProfileRepository.findByUserId(user.get().getId());
        Optional<NextOfKin> nextOfKin = nextOfKinRepository.findNextOfKinByuserProfile(userProfile.get());
        List<Telephone> listOfTelephoneNumbers = profileDTO.getTelephoneNumber();
        for (Telephone telephone : listOfTelephoneNumbers){
            Optional<Telephone> telephoneNumber = telephoneRepository.findByNumber(telephone.getNumber());
            if(telephoneNumber.isPresent()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("status","failure","message","telephone number already exists"));
            }
        }
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

    public Optional<User> getUserFromHeader(HttpHeaders headers){
        String token  = Objects.requireNonNull(headers.getFirst(HttpHeaders.AUTHORIZATION)).substring(7);
        String emailAddress = jwtUtilsImpl.getEmailFromJwtToken(token);
        return userRepository.findByemailAddress(emailAddress);
    }
}
