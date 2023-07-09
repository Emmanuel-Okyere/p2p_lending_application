package com.p2p.p2p_lending_application.loan.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanAcceptDTO {
    private BigDecimal interestRate;
}
