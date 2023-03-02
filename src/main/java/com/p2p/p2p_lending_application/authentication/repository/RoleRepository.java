package com.p2p.p2p_lending_application.authentication.repository;

import com.p2p.p2p_lending_application.authentication.model.ERole;
import com.p2p.p2p_lending_application.authentication.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(ERole name);
}
