package com.expensetracker.etrackerapi.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    
    private Long userId;
    private String userEmail;
    private String userFirstName;
    private String userLastName;
    private long userNumber;
    private LocalDateTime createdOn;
}
