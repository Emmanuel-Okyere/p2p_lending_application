package com.p2p.p2p_lending_application.models;

import com.p2p.p2p_lending_application.models.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "loan")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "borrower_id")
    private User borrower;
    @PositiveOrZero(message = "amount must be a positive number")
    @Column(precision=10, scale=2)
    private BigDecimal amount;
    @PositiveOrZero(message = "interest must be a positive number")
    @Column(precision=10, scale=2)
    private BigDecimal interestRate;
    @ManyToOne
    @JoinColumn(name = "lender_id")
    private User lender;
    private Boolean approved;
    @UpdateTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;

    public Loan(User borrower, BigDecimal amount, Boolean approved) {
        this.borrower = borrower;
        this.amount = amount;
        this.approved = approved;
    }
}
