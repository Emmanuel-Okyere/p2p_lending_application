package com.p2p.p2p_lending_application.dto.requestDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoanApproval {
    @NotBlank
    private Boolean isApproved;
}
