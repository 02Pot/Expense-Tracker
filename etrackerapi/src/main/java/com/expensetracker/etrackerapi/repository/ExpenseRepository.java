package com.expensetracker.etrackerapi.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expensetracker.etrackerapi.model.ExpensesModel;
import com.expensetracker.etrackerapi.model.UserModel;


public interface ExpenseRepository extends JpaRepository<ExpensesModel,Long>{
    
    List<ExpensesModel> findByUser(UserModel user);

}
