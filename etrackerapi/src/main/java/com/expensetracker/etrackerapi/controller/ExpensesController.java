package com.expensetracker.etrackerapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expensetracker.etrackerapi.model.ExpensesModel;
import com.expensetracker.etrackerapi.service.implementation.ExpenseServiceImpl;

@CrossOrigin("*")
@RestController
@RequestMapping("/expenses")
public class ExpensesController {
    
    @Autowired
    private ExpenseServiceImpl service;

    @GetMapping
    public List<ExpensesModel> getExpensesByUser(){
        return service.getExpensesByUserId();
    }
    @PostMapping
    public ExpensesModel createExpense(@RequestBody ExpensesModel expense) {
        return service.createExpense(expense);
    }
    @PutMapping("/{expensesId}")
    public ExpensesModel updateExpense(@RequestBody ExpensesModel expense,@PathVariable Long expensesId) {
        return service.updateExpense(expense, expensesId);
    }
    @DeleteMapping(value = "/{expensesId}")
    public void deleteExpense(@PathVariable Long expensesId){
        service.deleteExpense(expensesId);
    }
}
