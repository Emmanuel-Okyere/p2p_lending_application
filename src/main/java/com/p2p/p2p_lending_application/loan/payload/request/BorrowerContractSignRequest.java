package com.p2p.p2p_lending_application.loan.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BorrowerContractSignRequest {
    @NotNull
    private String borrowerSignature;

}
