package com.p2p.p2p_lending_application.authentication.repository;

import com.p2p.p2p_lending_application.authentication.model.RefreshToken;
import com.p2p.p2p_lending_application.authentication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    Optional<RefreshToken> findBytoken(String token);
    @Modifying
    int deleteByuser(User user);
}
