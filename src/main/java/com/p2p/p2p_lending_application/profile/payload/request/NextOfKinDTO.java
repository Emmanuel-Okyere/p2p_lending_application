package com.p2p.p2p_lending_application.profile.payload.request;

import com.p2p.p2p_lending_application.profile.model.UserProfile;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class NextOfKinDTO {
    private UserProfile userProfile;
    @NotNull
    private String fullName;
    @Email
    private String emailAddress;
}
