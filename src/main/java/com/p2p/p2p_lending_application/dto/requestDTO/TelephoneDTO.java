package com.p2p.p2p_lending_application.dto.requestDTO;

import com.p2p.p2p_lending_application.models.UserProfile;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class TelephoneDTO {
    @NotNull
    private String number;
    private UserProfile profileId;
}
