package com.p2p.p2p_lending_application.profile.model;

import com.p2p.p2p_lending_application.authentication.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.GeneratorType;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;
@Data
@Entity
@Table(name = "profile")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany
    private List<Telephone> telephoneNumber;
    @NotNull
    private Date dateOfBirth;
    @NotNull
    private String digitalAddress;
    @OneToOne
    @JoinColumn(name = "nextOfKin_id")
    private NextOfKin nextOfKin;
    @UpdateTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;
}
