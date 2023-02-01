package com.p2p.p2p_lending_application.loan.payload.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class LoanDTO {
    @NotNull
    @PositiveOrZero(message = "amount must be a positive number")
    @Column(precision=10, scale=2)
    private BigDecimal amount;
}
