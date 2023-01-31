package com.p2p.p2p_lending_application.profile.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
@Data
@Entity
@Table(name = "nextOfKin")
public class NextOfKin {
    @Id
    private Long id;
    @OneToOne(mappedBy = "nextOfKin")
    private UserProfile userProfile;
    @NotNull
    private String fullName;
    @Email
    private String emailAddress;
    @UpdateTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;
}
