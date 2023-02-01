package com.p2p.p2p_lending_application.profile.repository;

import com.p2p.p2p_lending_application.profile.model.NextOfKin;
import com.p2p.p2p_lending_application.profile.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NextOfKinRepository extends JpaRepository<NextOfKin,Long> {
    Optional<NextOfKin> findNextOfKinByuserProfile(UserProfile userProfile);
}
