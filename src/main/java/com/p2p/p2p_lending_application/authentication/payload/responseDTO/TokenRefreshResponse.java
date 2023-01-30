package com.p2p.p2p_lending_application.authentication.payload.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenRefreshResponse {
    private String token;
    private String refreshToken;
}
