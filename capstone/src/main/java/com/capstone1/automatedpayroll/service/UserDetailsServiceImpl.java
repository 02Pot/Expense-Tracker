package com.capstone1.automatedpayroll.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.capstone1.automatedpayroll.model.UserModel;
import com.capstone1.automatedpayroll.model.UserModelPrincipal;
import com.capstone1.automatedpayroll.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = userRepository.findByUserEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not Found");
        }
        return new UserModelPrincipal(user);
    }
    
    

}
