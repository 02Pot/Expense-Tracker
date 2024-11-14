package com.expensetracker.etrackerapi.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.expensetracker.etrackerapi.model.IncomeModel;
import com.expensetracker.etrackerapi.model.UserModel;
import com.expensetracker.etrackerapi.repository.IncomeRepository;
import com.expensetracker.etrackerapi.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;


@Service
public class IncomeServiceImpl {

    @Autowired
    private IncomeRepository incomeRepository;
    @Autowired
    private UserRepository userRepository;

    public UserModel getLoggedInUser() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUserEmail(userEmail);
    }

    public List<IncomeModel> getIncomeByUserId() {
        UserModel loggedInUser = getLoggedInUser();
        return incomeRepository.findByUser(loggedInUser);
    }

    public IncomeModel createIncome(IncomeModel income){
        UserModel loggedInUser = getLoggedInUser();
        income.setUser(loggedInUser);
        income.setIncomeDescription(income.getIncomeDescription());
        income.setIncomeCategory(income.getIncomeCategory());
        income.setIncomeAmount(income.getIncomeAmount());
        return incomeRepository.save(income);
    }

    public IncomeModel updateIncome(IncomeModel income, Long incomeId) {
        IncomeModel existingIncome = incomeRepository.findById(incomeId).orElseThrow(() -> new EntityNotFoundException("income not found"));
        UserModel loggedInUser = getLoggedInUser();
        if (!existingIncome.getUser().getuId().equals(loggedInUser.getuId())) {
            throw new AccessDeniedException("You are not authorized to update this income.");
        }

        if (income.getIncomeDescription() != null) {
            existingIncome.setIncomeDescription(income.getIncomeDescription());
        }
        if (income.getIncomeCategory() != null) {
            existingIncome.setIncomeCategory(income.getIncomeCategory());
        }
        if (income.getIncomeAmount() != null) {
            existingIncome.setIncomeAmount(income.getIncomeAmount());
        }
        return incomeRepository.save(existingIncome);
    }

    public void deleteIncome(Long incomeId){
        incomeRepository.deleteById(incomeId);
    }
}