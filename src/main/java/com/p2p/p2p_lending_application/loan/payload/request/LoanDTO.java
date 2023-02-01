package com.p2p.p2p_lending_application.loan.payload.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanDTO {
    @NotNull
    @PositiveOrZero(message = "amount must be a positive number")
    @Column(precision=10, scale=2)
    private BigDecimal amount;
}
