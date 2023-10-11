package com.p2p.p2p_lending_application.dto.requestDTO;

import com.p2p.p2p_lending_application.models.UserProfile;
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
