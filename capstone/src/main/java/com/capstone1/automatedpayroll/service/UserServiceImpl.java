package com.capstone1.automatedpayroll.service;

import java.util.List;

import com.capstone1.automatedpayroll.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.capstone1.automatedpayroll.exception.BadCredentialsException;
import com.capstone1.automatedpayroll.exception.EmailAlreadyExistsException;
import com.capstone1.automatedpayroll.model.UserModel;
import com.capstone1.automatedpayroll.repository.UserRepository;

@Service
public class UserServiceImpl{

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTServiceImpl jwtService;
    @Autowired
    private UserRepository userRepository;

    public UserModel register(UserModel user){
        if (userRepository.existsByUserEmail(user.getUserEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        return userRepository.save(user);
    }

    public String verify(UserDTO user){
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUserEmail(), user.getUserPassword())
            );
            return jwtService.generateToken(user.getUserEmail());
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
            }
    }

    public UserModel updateUser(Long userId, UserModel user) {
        UserModel existingUser = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (user.getUserLastName() != null) {
            existingUser.setUserLastName(user.getUserLastName());
        }
        if (user.getUserFirstName() != null) {
            existingUser.setUserLastName(user.getUserFirstName());
        }
        if (user.getUserPassword() != null) {
            existingUser.setUserPassword(user.getUserPassword());
        }
        if (user.getUserNumber() != -1) {
            existingUser.setUserNumber(user.getUserNumber());
        }
        return userRepository.save(existingUser);
    }

    public void deleteUser(Long userId){
        userRepository.deleteById(userId);
    }

    public UserModel getUser(Long userId){
        return userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("No User with Id of " + userId));
    }

    public List<UserModel> findAllUser(){
        List<UserModel> allUser = userRepository.findAll();
        return allUser;
    }

}