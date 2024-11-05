package com.expensetracker.etrackerapi.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expensetracker.etrackerapi.model.IncomeModel;
import com.expensetracker.etrackerapi.model.UserModel;


public interface IncomeRepository extends JpaRepository<IncomeModel,Long>{
    
    List<IncomeModel> findByUser(UserModel user);

}
