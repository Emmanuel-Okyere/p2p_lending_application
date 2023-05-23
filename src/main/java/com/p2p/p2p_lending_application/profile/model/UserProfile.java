package com.p2p.p2p_lending_application.profile.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.p2p.p2p_lending_application.authentication.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
@Data
@Entity
@Table(name = "profile")
@AllArgsConstructor
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private List<Telephone> telephoneNumber;
    private LocalDate dateOfBirth;

    private String digitalAddress;
    @OneToOne
    @JoinColumn(name = "nextOfKin_id")
    private NextOfKin nextOfKin;
    @JsonIgnore
    @UpdateTimestamp
    private Date createdAt;
    @JsonIgnore
    @UpdateTimestamp
    private Date updatedAt;
    public UserProfile(User user){
        this.user = user;
    }
    public UserProfile(){
    }
}
