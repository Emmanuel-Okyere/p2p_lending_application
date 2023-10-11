package com.p2p.p2p_lending_application.dto.responseDTO;

import com.p2p.p2p_lending_application.models.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserProfileResponse {
    private String status;
    private String message;
    private UserProfile userProfile;
}
