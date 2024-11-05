package com.expensetracker.etrackerapi.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IncomeDTO {

    private Long incomeId;
    private Long incomeAmount;
    private String incomeDescription;
    private String incomeCategory;
    private List<String> incomeCategories;
}
