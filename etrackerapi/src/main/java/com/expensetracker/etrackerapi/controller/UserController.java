package com.expensetracker.etrackerapi.controller;

import java.util.Collections;
import java.util.List;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expensetracker.etrackerapi.model.UserModel;
import com.expensetracker.etrackerapi.service.implementation.UserServiceImpl;

@CrossOrigin("*")
@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private UserServiceImpl service;

    @GetMapping(value = "/{userId}")
    public ResponseEntity<UserModel> getUser(@PathVariable Long userId){
        UserModel user = service.getUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/allUser")
    public List<UserModel> getListUser() {
        return service.findAllUser();
    }
    
    @PostMapping("/register")
    public ResponseEntity<UserModel> register(@RequestBody UserModel user) {
        UserModel registeredUser = service.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserModel user) {
        String token = service.verify(user);
        if("not authenticated".equals(token)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(Collections.singletonMap("error", "Incorrect Username or Password"));
        }
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }

    @PutMapping(value = "/{userId}")
    public ResponseEntity<UserModel> updateUser(@PathVariable Long userId,@RequestBody UserModel user){
        UserModel updatedUser = service.updateUser(userId, user);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @DeleteMapping(value = "/{userId}")
    public void deleteUser(@PathVariable Long userId){
        service.deleteUser(userId);
    }

}
