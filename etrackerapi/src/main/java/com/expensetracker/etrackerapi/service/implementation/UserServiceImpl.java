package com.expensetracker.etrackerapi.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.expensetracker.etrackerapi.exception.BadCredentialsException;
import com.expensetracker.etrackerapi.exception.EmailAlreadyExistsException;
import com.expensetracker.etrackerapi.model.UserModel;
import com.expensetracker.etrackerapi.repository.UserRepository;

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

    public String verify(UserModel user){
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUserEmail(), user.getUserPassword())
            );
            if (authentication.isAuthenticated()) {
                return jwtService.generateToken(user.getUserEmail());
            } else {
                throw new BadCredentialsException("Not authenticated");
            }
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username or password");
            }
        }
    

    public UserModel updateUser(Long userId, UserModel user) {
        UserModel existingUser = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (user.getUserEmail() != null) {
            existingUser.setUserEmail(user.getUserEmail());
        }
        if (user.getuserLastName() != null) {
            existingUser.setuserLastName(user.getuserLastName());
        }
        if (user.getuserFirstName() != null) {
            existingUser.setuserLastName(user.getuserFirstName());
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