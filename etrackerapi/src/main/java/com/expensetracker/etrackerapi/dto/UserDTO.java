package com.expensetracker.etrackerapi.dto;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    
    private Long userId;
    private String userEmail;
    private String userFirstName;
    private String userLastName;
    private long userNumber;
    private LocalDateTime createdOn;
}
