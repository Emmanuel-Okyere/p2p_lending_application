package com.p2p.p2p_lending_application.profile.payload.request;

import com.p2p.p2p_lending_application.profile.model.UserProfile;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
@Data
public class NextOfKinDTO {
    private UserProfile userProfile;
    @NotNull
    private String fullName;
    @Email
    private String emailAddress;
}
