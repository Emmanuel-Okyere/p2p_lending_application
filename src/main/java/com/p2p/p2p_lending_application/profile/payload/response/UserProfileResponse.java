package com.p2p.p2p_lending_application.profile.payload.response;

import com.p2p.p2p_lending_application.profile.model.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserProfileResponse {
    private String status;
    private String message;
    private UserProfile userProfile;
}
