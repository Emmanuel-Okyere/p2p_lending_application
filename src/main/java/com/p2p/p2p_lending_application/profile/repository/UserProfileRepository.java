package com.p2p.p2p_lending_application.profile.repository;

import com.p2p.p2p_lending_application.profile.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface UserProfileRepository extends JpaRepository<UserProfile,Long> {

}
