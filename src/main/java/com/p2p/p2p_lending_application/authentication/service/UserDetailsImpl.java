package com.p2p.p2p_lending_application.authentication.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.p2p.p2p_lending_application.authentication.model.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserDetailsImpl implements UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;

    private String fullName;

    private String email;


    @JsonIgnore
    private String password;

//    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id,String username, String name, String email, String password) {
        this.id = id;
        this.fullName = name;
        this.email = email;
        this.password = password;
        this.username = username;
//        this.authorities = authorities;
    }

    public static UserDetailsImpl build(User user){
//        List<GrantedAuthority> authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());
        return new UserDetailsImpl(user.getId(),user.getUsername(), user.getFullName(), user.getEmailAddress(), user.getPassword());
    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return this.authorities;
//    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.username;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
