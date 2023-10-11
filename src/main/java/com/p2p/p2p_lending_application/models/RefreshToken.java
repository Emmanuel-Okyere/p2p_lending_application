package com.p2p.p2p_lending_application.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"token"})})
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    private String token;
    private Instant expiryDate;

}
