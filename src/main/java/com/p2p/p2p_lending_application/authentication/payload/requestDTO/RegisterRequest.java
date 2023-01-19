package com.p2p.p2p_lending_application.authentication.payload.requestDTO;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String username;
    private String fullName;
    @Email
    private String emailAddress;
    private String password;
    private String confirmPassword;
}
