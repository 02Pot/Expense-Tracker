package com.expensetracker.etrackerapi.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDTO {
    
    private Long expensesId;
    private BigDecimal expensesAmount;
    private String expensesDescription;
    private String expensesCategory;
    private List<String> expensesCategories;
}
