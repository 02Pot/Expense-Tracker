package com.capstone1.automatedpayroll.model;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserModelPrincipal implements UserDetails{
    
    private UserModel user;

    public UserModelPrincipal(UserModel user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getPassword() {
        return user.getUserPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserEmail();
    }

    // @Override
    // public boolean isAccountNonExpired() {
    //     return user.isAccountNonExpired();
    // }

    // @Override
    // public boolean isAccountNonLocked() {
    //     return user.isAccountNonLocked();
    // }

    // @Override
    // public boolean isCredentialsNonExpired() {
    //     return user.isCredentialsNonExpired();
    // }

    // @Override
    // public boolean isEnabled() {
    //     return user.isEnabled();
    // }

    
}
