package com.expensetracker.etrackerapi.service;

import com.expensetracker.etrackerapi.model.ExpensesModel;
import com.expensetracker.etrackerapi.model.UserModel;
import com.expensetracker.etrackerapi.repository.ExpenseRepository;
import com.expensetracker.etrackerapi.repository.UserRepository;
import com.expensetracker.etrackerapi.service.implementation.ExpenseServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExpenseServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private ExpenseServiceImpl expenseService;

    private UserModel userModel;
    private String email = "testing@gmail.com";

    @BeforeEach
    public void init(){
        userModel = UserModel.builder()
                .userEmail(email)
                .userFirstName("te")
                .userLastName("st")
                .userNumber(25)
                .userPassword("test")
                .build();
    }

    private void mockJwt(String email){
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(email);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void ExpenseService_GetLogUser_ReturnUser(){
        //Arrange
        when(userRepository.findByUserEmail(email)).thenReturn(userModel);
        mockJwt(email);

        //Act
        UserModel user = expenseService.getLoggedInUser();
        //Assert
        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(userModel).isEqualTo(user);
    }

    @Test
    public void ExpenseService_GetExpenseById_ReturnExpense(){
        when(userRepository.findByUserEmail(email)).thenReturn(userModel);
        mockJwt(email);

        UserModel user = expenseService.getLoggedInUser();

        ExpensesModel expense1 = ExpensesModel.builder()
                .expensesCategory("Rent")
                .expensesAmount(BigDecimal.valueOf(7000))
                .expensesDescription("Test")
                .build();
        ExpensesModel expense2 = ExpensesModel.builder()
                .expensesCategory("Groceries")
                .expensesAmount(BigDecimal.valueOf(8000))
                .expensesDescription("Test")
                .build();

        List<ExpensesModel> expenses = Arrays.asList(expense1,expense2);

        when(expenseRepository.findByUser(user)).thenReturn(expenses);

        List<ExpensesModel> result = expenseService.getExpensesByUserId();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.size()).isEqualTo(2);
    }

    @Test
    public void ExpenseService_CreateExpense_ReturnExpense(){
        when(userRepository.findByUserEmail(email)).thenReturn(userModel);
        mockJwt(email);
        UserModel user = expenseService.getLoggedInUser();

        ExpensesModel expensesModel = ExpensesModel.builder()
                .expensesAmount(BigDecimal.valueOf(1004))
                .expensesCategory("Test")
                .expensesDescription("Test")
                .build();

        when(expenseService.getLoggedInUser()).thenReturn(userModel);
        when(expenseRepository.save(expensesModel)).thenReturn(expensesModel);

        ExpensesModel saveExpense = expenseService.createExpense(expensesModel);

        Assertions.assertThat(saveExpense).isNotNull();
        Assertions.assertThat(saveExpense.getUser()).isEqualTo(userModel);
    }

    @Test
    public void  ExpenseService_UpdateExpense_ReturnExpense(){
        Long expenseId = 1L;
        userModel.setuId(1L);

        when(userRepository.findByUserEmail(email)).thenReturn(userModel);
        mockJwt(email);
        UserModel user = expenseService.getLoggedInUser();

        ExpensesModel existingExpense = ExpensesModel.builder()
                .expensesCategory("Original")
                .expensesAmount(BigDecimal.valueOf(6000))
                .expensesDescription("Original")
                .user(userModel)
                .build();

        when(expenseRepository.findById(expenseId)).thenReturn(Optional.of(existingExpense));

        ExpensesModel updatedExpense = ExpensesModel.builder()
                .expensesCategory("Test")
                .expensesAmount(BigDecimal.valueOf(6000))
                .expensesDescription("Test")
                .build();

        when(expenseRepository.save(existingExpense)).thenReturn(existingExpense);

        ExpensesModel result = expenseService.updateExpense(updatedExpense,expenseId);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getExpensesCategory()).isEqualTo("Test");
        Assertions.assertThat(result.getExpensesDescription()).isEqualTo("Test");
    }

    @Test
    public void ExpenseService_DeleteExpense_ReturnExpense(){
        Long expenseId = 1L;
        ExpensesModel expensesModel = ExpensesModel.builder()
                .expensesCategory("Test")
                .expensesAmount(BigDecimal.valueOf(6000))
                .expensesDescription("Test")
                .build();

        when(expenseRepository.findById(expenseId)).thenReturn(Optional.of(expensesModel));
        expenseService.deleteExpense(expenseId);
        when(expenseRepository.findById(expenseId)).thenReturn(Optional.empty());

        Optional<ExpensesModel> result = expenseRepository.findById(expenseId);
        Assertions.assertThat(result).isEmpty();
    }

}
