package com.p2p.p2p_lending_application.profile.payload.request;

import com.p2p.p2p_lending_application.profile.model.UserProfile;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class TelephoneDTO {
    @NotNull
    private String number;
    private UserProfile profileId;
}
