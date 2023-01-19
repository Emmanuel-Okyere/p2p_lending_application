package com.p2p.p2p_lending_application.authentication.service;


import com.p2p.p2p_lending_application.authentication.model.User;
import com.p2p.p2p_lending_application.authentication.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByemailAddress(email).orElseThrow(()-> new UsernameNotFoundException("User not found with username"+ email));
        return UserDetailsImpl.build(user);
    }
}
