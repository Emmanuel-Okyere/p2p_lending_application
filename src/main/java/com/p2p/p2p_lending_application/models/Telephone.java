package com.p2p.p2p_lending_application.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
@Data
@Entity
@Table(name = "telephone",uniqueConstraints = {
        @UniqueConstraint(columnNames = {"number"})
})
public class Telephone {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   @NotNull
   @Length(min = 10,message = "number format not correct")
   private String number;
   @JsonIgnore
   @ManyToOne
   @JoinColumn(name = "profile_id")
   private UserProfile profileId;
   private Boolean verified;
   @UpdateTimestamp
   @JsonIgnore
   private Date createdAt;
   @UpdateTimestamp
   @JsonIgnore
   private Date updatedAt;
}
