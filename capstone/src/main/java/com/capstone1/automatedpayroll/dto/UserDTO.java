package com.capstone1.automatedpayroll.dto;

import java.time.LocalDateTime;


public class UserDTO {
    
    private Long userId;
    private String userEmail;
    private String userPassword;
    private String userFirstName;
    private String userLastName;
    private String userWorkShift;
    private long userNumber;
    private LocalDateTime createdOn;

    public UserDTO(){}

    public UserDTO(Long userId, String userEmail, String userPassword,
                   String userFirstName, String userLastName,
                   String userWorkShift, long userNumber,
                   LocalDateTime createdOn) {

        this.userId = userId;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userWorkShift = userWorkShift;
        this.userNumber = userNumber;
        this.createdOn = createdOn;
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserWorkShift() {
        return userWorkShift;
    }

    public void setUserWorkShift(String userWorkShift) {
        this.userWorkShift = userWorkShift;
    }

    public long getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(long userNumber) {
        this.userNumber = userNumber;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    // ================= BUILDER =================
    public static class Builder {
        private Long userId;
        private String userEmail;
        private String userPassword;
        private String userFirstName;
        private String userLastName;
        private String userWorkShift;
        private long userNumber;
        private LocalDateTime createdOn;

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder userEmail(String userEmail) {
            this.userEmail = userEmail;
            return this;
        }

        public Builder userPassword(String userPassword) {
            this.userPassword = userPassword;
            return this;
        }

        public Builder userFirstName(String userFirstName) {
            this.userFirstName = userFirstName;
            return this;
        }

        public Builder userLastName(String userLastName) {
            this.userLastName = userLastName;
            return this;
        }

        public Builder userWorkShift(String userWorkShift) {
            this.userWorkShift = userWorkShift;
            return this;
        }

        public Builder userNumber(long userNumber) {
            this.userNumber = userNumber;
            return this;
        }

        public Builder createdOn(LocalDateTime createdOn) {
            this.createdOn = createdOn;
            return this;
        }

        public UserDTO build() {
            return new UserDTO(
                    userId,
                    userEmail,
                    userPassword,
                    userFirstName,
                    userLastName,
                    userWorkShift,
                    userNumber,
                    createdOn
            );
        }
    }

    public static Builder builder() {
        return new Builder();
    }

}
