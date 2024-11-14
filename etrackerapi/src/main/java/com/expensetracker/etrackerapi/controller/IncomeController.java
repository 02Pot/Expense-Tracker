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

import com.expensetracker.etrackerapi.model.IncomeModel;
import com.expensetracker.etrackerapi.service.implementation.IncomeServiceImpl;

@RestController
@RequestMapping("/income")
@CrossOrigin("*")
public class IncomeController {
    
    @Autowired
    private IncomeServiceImpl service;

    @GetMapping
    public List<IncomeModel> getIncomeByUser(){
        return service.getIncomeByUserId();
    }
    
    @PostMapping
    public IncomeModel createIncome(@RequestBody IncomeModel income) {
        return service.createIncome(income);
    }
    @PutMapping("/{incomeId}")
    public IncomeModel updateIncome(@RequestBody IncomeModel income,@PathVariable Long incomeId) {
        return service.updateIncome(income, incomeId);
    }
    @DeleteMapping(value = "/{incomeId}")
    public void deleteIncome(@PathVariable Long incomeId){
        service.deleteIncome(incomeId);
    }

}
