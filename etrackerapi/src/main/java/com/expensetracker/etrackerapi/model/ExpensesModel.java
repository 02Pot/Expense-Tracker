package com.expensetracker.etrackerapi.model;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Data
@NoArgsConstructor
@Table(name = "Expenses")
public class ExpensesModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long expensesId;
    private BigDecimal expensesAmount;
    private String expensesDescription;
    private String expensesCategory;
    @CreationTimestamp
    private Date expenseIncurred;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserModel user;
}
