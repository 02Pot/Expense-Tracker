package com.expensetracker.etrackerapi.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.expensetracker.etrackerapi.model.ExpensesModel;
import com.expensetracker.etrackerapi.model.UserModel;
import com.expensetracker.etrackerapi.repository.ExpenseRepository;
import com.expensetracker.etrackerapi.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ExpenseServiceImpl {

    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private UserRepository userRepository;

    private UserModel getLoggedInUser() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUserEmail(userEmail);
    }

    public List<ExpensesModel> getExpensesByUserId() {
        UserModel loggedInUser = getLoggedInUser();
        return expenseRepository.findByUser(loggedInUser);
    }

    public ExpensesModel createExpense(ExpensesModel expense){
        UserModel loggedInUser = getLoggedInUser();
        expense.setUser(loggedInUser);
        expense.setExpensesCategory(expense.getExpensesCategory());
        expense.setExpensesDescription(expense.getExpensesDescription());
        expense.setExpensesAmount(expense.getExpensesAmount());
        return expenseRepository.save(expense);
    }

    public ExpensesModel updateExpense(ExpensesModel expense, Long expensesId) {
        ExpensesModel existingExpense = expenseRepository.findById(expensesId).orElseThrow(() -> new EntityNotFoundException("Expense not found"));
        UserModel loggedInUser = getLoggedInUser();
        if (!existingExpense.getUser().getuId().equals(loggedInUser.getuId())) {
            throw new AccessDeniedException("You are not authorized to update this expense.");
        }

        if (expense.getExpensesCategory() != null) {
            existingExpense.setExpensesCategory(expense.getExpensesCategory());
        }
        if (expense.getExpensesDescription() != null) {
            existingExpense.setExpensesDescription(expense.getExpensesDescription());
        }
        if (expense.getExpensesAmount() != null) {
            existingExpense.setExpensesAmount(expense.getExpensesAmount());
        }
        return expenseRepository.save(existingExpense);
    }

    public void deleteExpense(Long expenseId){
        expenseRepository.deleteById(expenseId);
    }
}