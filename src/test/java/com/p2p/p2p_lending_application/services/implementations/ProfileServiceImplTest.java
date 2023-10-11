package com.p2p.p2p_lending_application.services.implementations;

import com.p2p.p2p_lending_application.models.User;
import com.p2p.p2p_lending_application.repositories.UserRepository;
import com.p2p.p2p_lending_application.models.NextOfKin;
import com.p2p.p2p_lending_application.models.Telephone;
import com.p2p.p2p_lending_application.models.UserProfile;
import com.p2p.p2p_lending_application.dto.requestDTO.ProfileDTO;
import com.p2p.p2p_lending_application.repositories.NextOfKinRepository;
import com.p2p.p2p_lending_application.repositories.TelephoneRepository;
import com.p2p.p2p_lending_application.repositories.UserProfileRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
@SpringBootTest
class ProfileServiceImplTest {
    @MockBean
    private UserProfileRepository userProfileRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private HttpHeaders headers;
    @MockBean
    private JwtUtilsImpl jwtUtilsImpl;
    @MockBean
    private TelephoneRepository telephoneRepository;
    @MockBean
    private NextOfKinRepository nextOfKinRepository;
    @Autowired
    private ProfileServiceImpl profileServiceImpl;

    @Test
    void usersCanGetTheirProfileWhenThereIsOne() {
        Optional<User> user = Optional.of(new User("Emmanuel",
                "Emmanuel Okyere Gyateng",
                "emmna@gmail.com",
                "password",
                "password"));
        user.get().setApproved(false);
        Optional<UserProfile> profile = Optional.of(new UserProfile(user.get()));
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbjMyNEBnbWFpbC5jb20iLCJleHAiOjE2NzUyNTQxOTksImlhdCI6MTY3NTI1MDU5OX0.WjjFbG2vyMzud2rWum7aroagyENTfOZbV0d_OkLcsAM0CKbRHORTtutO0QBqxat_egSPKRpPGBjnlnX30jK-dA";
        Mockito.when(headers.getFirst(HttpHeaders.AUTHORIZATION)).thenReturn(token);
        Mockito.doReturn("gyateng94@gmail.com").when(jwtUtilsImpl).getEmailFromJwtToken(any());
        Mockito.doReturn(user).when(userRepository).findByemailAddress(any());
        Mockito.doReturn(profile).when(userProfileRepository).findByUserId(user.get().getId());
        ResponseEntity<?> response = profileServiceImpl.getUserProfile(headers);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void createUserProfileWhenANewNumberIsBeingAdded() {
        NextOfKin nextOfKin = new NextOfKin();
        nextOfKin.setFullName("ere");
        nextOfKin.setEmailAddress("gaye@gma.co");
        Telephone telephone = new Telephone();
        telephone.setNumber("343434343434");
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setDigitalAddress("GA-1022-101");
        profileDTO.setDateOfBirth(LocalDateTime.now());
        profileDTO.setNextOfKin(nextOfKin);
        profileDTO.setTelephoneNumber(List.of(telephone));
        Optional<User> user = Optional.of(new User("Emmanuel",
                "Emmanuel Okyere Gyateng",
                "emmna@gmail.com",
                "password",
                "password"));
        user.get().setApproved(false);
        Optional<UserProfile> profile = Optional.of(new UserProfile(user.get()));
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbjMyNEBnbWFpbC5jb20iLCJleHAiOjE2NzUyNTQxOTksImlhdCI6MTY3NTI1MDU5OX0.WjjFbG2vyMzud2rWum7aroagyENTfOZbV0d_OkLcsAM0CKbRHORTtutO0QBqxat_egSPKRpPGBjnlnX30jK-dA";
        Mockito.when(headers.getFirst(HttpHeaders.AUTHORIZATION)).thenReturn(token);
        Mockito.doReturn("gyateng94@gmail.com").when(jwtUtilsImpl).getEmailFromJwtToken(any());
        Mockito.doReturn(user).when(userRepository).findByemailAddress(any());
        Mockito.doReturn(profile).when(userProfileRepository).findByUserId(user.get().getId());
        Mockito.doReturn(Optional.of(new NextOfKin())).when(nextOfKinRepository).findNextOfKinByuserProfile(profile.get());
        Mockito.doReturn(List.of(new Telephone())).when(telephoneRepository).findAllByProfileId(profile.get());
        profileServiceImpl.createUserProfile(profileDTO, headers);
        Mockito.verify(telephoneRepository, Mockito.times(1)).save(any());
    }

    @Test
    void createUserProfileWhenANextOfKingIsBeingAdded() {
        NextOfKin nextOfKin = new NextOfKin();
        nextOfKin.setFullName("ere");
        nextOfKin.setEmailAddress("gaye@gma.co");
        Telephone telephone = new Telephone();
        telephone.setNumber("343434343434");
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setDigitalAddress("GA-1022-101");
        profileDTO.setDateOfBirth(LocalDateTime.now());
        profileDTO.setNextOfKin(nextOfKin);
        profileDTO.setTelephoneNumber(List.of(telephone));
        Optional<User> user = Optional.of(new User("Emmanuel",
                "Emmanuel Okyere Gyateng",
                "emmna@gmail.com",
                "password",
                "password"));
        user.get().setApproved(false);
        Optional<UserProfile> profile = Optional.of(new UserProfile(user.get()));
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbjMyNEBnbWFpbC5jb20iLCJleHAiOjE2NzUyNTQxOTksImlhdCI6MTY3NTI1MDU5OX0.WjjFbG2vyMzud2rWum7aroagyENTfOZbV0d_OkLcsAM0CKbRHORTtutO0QBqxat_egSPKRpPGBjnlnX30jK-dA";
        Mockito.when(headers.getFirst(HttpHeaders.AUTHORIZATION)).thenReturn(token);
        Mockito.doReturn("gyateng94@gmail.com").when(jwtUtilsImpl).getEmailFromJwtToken(any());
        Mockito.doReturn(user).when(userRepository).findByemailAddress(any());
        Mockito.doReturn(profile).when(userProfileRepository).findByUserId(user.get().getId());
        Mockito.doReturn(Optional.empty()).when(nextOfKinRepository).findNextOfKinByuserProfile(profile.get());
        Mockito.doReturn(List.of(new Telephone())).when(telephoneRepository).findAllByProfileId(profile.get());
        profileServiceImpl.createUserProfile(profileDTO, headers);
        Mockito.verify(nextOfKinRepository, Mockito.times(1)).save(any());
    }
    @Test
    void createUserProfileWhenUserNextOfKinAndTelephoneIsNull() {
        NextOfKin nextOfKin = new NextOfKin();
        nextOfKin.setFullName("ere");
        nextOfKin.setEmailAddress("gaye@gma.co");
        Telephone telephone = new Telephone();
        telephone.setNumber("343434343434");
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setDigitalAddress("GA-1022-101");
        profileDTO.setDateOfBirth(LocalDateTime.now());
        profileDTO.setNextOfKin(nextOfKin);
        profileDTO.setTelephoneNumber(List.of(telephone));
        Optional<User> user = Optional.of(new User("Emmanuel",
                "Emmanuel Okyere Gyateng",
                "emmna@gmail.com",
                "password",
                "password"));
        user.get().setApproved(false);
        Optional<UserProfile> profile = Optional.of(new UserProfile(user.get()));
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbjMyNEBnbWFpbC5jb20iLCJleHAiOjE2NzUyNTQxOTksImlhdCI6MTY3NTI1MDU5OX0.WjjFbG2vyMzud2rWum7aroagyENTfOZbV0d_OkLcsAM0CKbRHORTtutO0QBqxat_egSPKRpPGBjnlnX30jK-dA";
        Mockito.when(headers.getFirst(HttpHeaders.AUTHORIZATION)).thenReturn(token);
        Mockito.doReturn("gyateng94@gmail.com").when(jwtUtilsImpl).getEmailFromJwtToken(any());
        Mockito.doReturn(user).when(userRepository).findByemailAddress(any());
        Mockito.doReturn(profile).when(userProfileRepository).findByUserId(user.get().getId());
        Mockito.doReturn(Optional.of(new NextOfKin())).when(nextOfKinRepository).findNextOfKinByuserProfile(profile.get());
        Mockito.doReturn(List.of(new Telephone())).when(telephoneRepository).findAllByProfileId(profile.get());
        ResponseEntity<?> response = profileServiceImpl.createUserProfile(profileDTO, headers);
        assertEquals(response.getStatusCode(),HttpStatus.OK);
    }
}