package com.p2p.p2p_lending_application.profile.repository;

import com.p2p.p2p_lending_application.profile.model.Telephone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelephoneRepository extends JpaRepository<Telephone,Long> {

}
