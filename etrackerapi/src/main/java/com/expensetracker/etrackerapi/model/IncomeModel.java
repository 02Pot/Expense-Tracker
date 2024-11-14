package com.expensetracker.etrackerapi.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "income")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class IncomeModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long incomeId;
    private Long incomeAmount;
    private String incomeDescription;
    private String incomeCategory;
    @CreationTimestamp
    private LocalDateTime createdOn;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserModel user;
}

