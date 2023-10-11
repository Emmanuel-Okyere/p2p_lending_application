package com.p2p.p2p_lending_application.dto.responseDTO;

import lombok.Data;
@Data
public class UserResponses {
    private String status;
    private String message;

    public UserResponses(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
