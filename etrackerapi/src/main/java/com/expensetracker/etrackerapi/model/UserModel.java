package com.expensetracker.etrackerapi.model;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
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
    @CreationTimestamp
    private LocalDateTime createdOn;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<ExpensesModel> expensesModels;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<IncomeModel> incomeModels;

    public UserModel(Long userId, String userEmail, String userFirstName, String userLastName, long userNumber,
            LocalDateTime createdOn) {
        this.uId = userId;
        this.userEmail = userEmail;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userNumber = userNumber;
        this.createdOn = createdOn;
    }

    public String getuserFirstName() {
        return userFirstName;
    }

    public void setuserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getuserLastName() {
        return userLastName;
    }

    public void setuserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public long getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(long userNumber) {
        this.userNumber = userNumber;
    }
    
    public Long getuId() {
        return uId;
    }
    public void setuId(Long uId) {
        this.uId = uId;
    }
    public String getUserEmail() {
        return userEmail;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public String getUserPassword() {
        return userPassword;
    }
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
    public LocalDateTime getCreatedOn() {
        return createdOn;
    }
    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

}
