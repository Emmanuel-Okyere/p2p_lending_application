package com.p2p.p2p_lending_application.profile.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
@Data
@Entity
@Table(name = "telephone")
public class Telephone {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   private String number;
   @JsonIgnoreProperties
   @ManyToOne
   @JoinColumn(name = "profile_id")
   private UserProfile profileId;
   private Boolean verified;
   @UpdateTimestamp
   private Date createdAt;
   @UpdateTimestamp
   private Date updatedAt;
}
