package com.p2p.p2p_lending_application.repositories;

import com.p2p.p2p_lending_application.models.NextOfKin;
import com.p2p.p2p_lending_application.models.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NextOfKinRepository extends JpaRepository<NextOfKin,Long> {
    Optional<NextOfKin> findNextOfKinByuserProfile(UserProfile userProfile);
}
