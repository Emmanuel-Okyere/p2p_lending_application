package com.p2p.p2p_lending_application.profile.payload.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.p2p.p2p_lending_application.profile.model.UserProfile;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;
@Data
public class TelephoneDTO {
    private String number;
    private UserProfile profileId;
}
