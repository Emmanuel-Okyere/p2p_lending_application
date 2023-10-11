package com.p2p.p2p_lending_application.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
@Data
@Entity
@Table(name = "nextOfKin")
@NoArgsConstructor
public class NextOfKin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @OneToOne(mappedBy = "nextOfKin")
    private UserProfile userProfile;
    @NotNull
    private String fullName;
    @Email
    @NotNull
    private String emailAddress;
    @UpdateTimestamp
    @JsonIgnore
    private Date createdAt;
    @UpdateTimestamp
    @JsonIgnore
    private Date updatedAt;

    public NextOfKin(UserProfile userProfile, String fullName, String emailAddress) {
        this.userProfile = userProfile;
        this.fullName = fullName;
        this.emailAddress = emailAddress;
    }
}

