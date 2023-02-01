package com.p2p.p2p_lending_application.authentication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Data
@RequiredArgsConstructor
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"emailAddress","username"})})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String fullName;
    @Email
    private String emailAddress;
    @JsonIgnore
    private String password;

    public User(String username, String fullName, String emailAddress, String password, String confirmPassword) {
        this.username = username;
        this.fullName = fullName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    @Transient
    @JsonIgnore
    private String confirmPassword;
    @JsonIgnore
    private Boolean verified;
    private Boolean approved;
    @JsonIgnore
    @CreationTimestamp
    private Date createdAt;
    @JsonIgnore
    @UpdateTimestamp
    private Date updatedAt;
}
