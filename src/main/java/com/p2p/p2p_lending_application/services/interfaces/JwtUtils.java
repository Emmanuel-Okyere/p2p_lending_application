package com.p2p.p2p_lending_application.services.interfaces;

import org.springframework.security.core.Authentication;

public interface JwtUtils {
    String generateJwtToken(Authentication authentication);
    String generateTokenFromEmailAddress(String emailAddress);
    boolean validateJwtToken(String jwt);
    String getEmailFromJwtToken(String jwt);
}
