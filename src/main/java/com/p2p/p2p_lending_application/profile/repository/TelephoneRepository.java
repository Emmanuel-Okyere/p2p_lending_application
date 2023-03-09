package com.p2p.p2p_lending_application.profile.repository;

import com.p2p.p2p_lending_application.profile.model.Telephone;
import com.p2p.p2p_lending_application.profile.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TelephoneRepository extends JpaRepository<Telephone,Long> {
    List<Telephone> findAllByProfileId(UserProfile profileId);
    Optional<Telephone> findByNumber(String number);

}
