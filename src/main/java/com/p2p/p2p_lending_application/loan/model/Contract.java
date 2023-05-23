package com.p2p.p2p_lending_application.loan.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.Date;

import static java.time.temporal.ChronoUnit.DAYS;

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
    private LocalDate endDate;
    @Transient
    private Long remainingDays;
    private String paymentType;
    @UpdateTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;

    public Contract(Loan loanId, String paymentType) {
        this.loanId = loanId;
        this.paymentType = paymentType;
    }
    public Long getRemainingDays(){
        LocalDate nowDate = LocalDate.now();
        return DAYS.between(nowDate,this.endDate);
    }
}
