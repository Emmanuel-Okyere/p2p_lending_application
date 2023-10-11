package com.p2p.p2p_lending_application.controllers;

import com.p2p.p2p_lending_application.dto.requestDTO.ProfileDTO;
import com.p2p.p2p_lending_application.services.implementations.ProfileServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/profile")
public class ProfileController {
    @Autowired
    private ProfileServiceImpl profileServiceImpl;
//    @Autowired
//    private
    @GetMapping()
    public ResponseEntity<?> getUserProfile(@RequestHeader HttpHeaders header){
        return profileServiceImpl.getUserProfile(header);
    }

    @PostMapping()
    public ResponseEntity<?> createUserProfile(@RequestBody @Valid ProfileDTO profileDTO, @RequestHeader HttpHeaders header){
        return profileServiceImpl.createUserProfile(profileDTO,header);
    }
//    @PatchMapping("/{portfolioID}")
//    public ResponseEntity<?> updateUserProfile(@RequestBody UserProfile userProfile, @PathVariable Long portfolioID){
//        return profileService.updateUserProfile(userProfile,portfolioID);
//    }
}
