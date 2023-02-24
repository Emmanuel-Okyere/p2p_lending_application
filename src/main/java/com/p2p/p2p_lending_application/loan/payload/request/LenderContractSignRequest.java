package com.p2p.p2p_lending_application.loan.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class LenderContractSignRequest {
    @NotNull
    private String lenderSignature;
    @NotNull
    private Date endDate;
    @NotNull
    private String paymentType;
}
