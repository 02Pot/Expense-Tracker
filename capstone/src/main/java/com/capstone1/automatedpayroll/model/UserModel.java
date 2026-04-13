package com.capstone1.automatedpayroll.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UserModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uId;
    private String userEmail;
    private String userPassword;
    private String userFirstName;
    private String userLastName;
    private long userNumber;

    public UserModel(){}

    public UserModel(Long userId, String userEmail, String userFirstName, String userLastName, long userNumber) {
        this.uId = userId;
        this.userEmail = userEmail;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userNumber = userNumber;

    }

    //SETTERS
    public void setUserFirstName(String userFirstName) {this.userFirstName = userFirstName;}
    public void setUserLastName(String userLastName) { this.userLastName = userLastName; }
    public void setUserNumber(long userNumber) {
        this.userNumber = userNumber;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    //GETTERS
    public String getUserFirstName() {return userFirstName;}
    public String getUserLastName() {
        return userLastName;
    }
    public long getUserNumber() {
        return userNumber;
    }
    public Long getuId() {
        return uId;
    }
    public String getUserEmail() {
        return userEmail;
    }
    public String getUserPassword() {
        return userPassword;
    }

}
