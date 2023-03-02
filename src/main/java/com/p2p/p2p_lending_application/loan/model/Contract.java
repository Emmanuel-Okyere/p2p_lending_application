package com.p2p.p2p_lending_application.loan.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long iD;
    @OneToOne
    private Loan loanId;
    private String borrowerSignature;
    private String lenderSignature;
    private Date duration;
    private String paymentType;
    @UpdateTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;

    public Contract(Loan loanId, String paymentType) {
        this.loanId = loanId;
        this.paymentType = paymentType;
    }
}
