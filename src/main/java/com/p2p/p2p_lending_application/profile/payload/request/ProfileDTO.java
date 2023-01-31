package com.p2p.p2p_lending_application.profile.payload.request;

import com.p2p.p2p_lending_application.authentication.model.User;
import com.p2p.p2p_lending_application.profile.model.NextOfKin;
import com.p2p.p2p_lending_application.profile.model.Telephone;
import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class ProfileDTO {

    private User user;
    private List<Telephone> telephoneNumber;
    private Date dateOfBirth;
    private String digitalAddress;
    private NextOfKin nextOfKin;
}
