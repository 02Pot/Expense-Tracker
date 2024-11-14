package com.expensetracker.etrackerapi.service;

import com.expensetracker.etrackerapi.model.IncomeModel;
import com.expensetracker.etrackerapi.model.UserModel;
import com.expensetracker.etrackerapi.repository.IncomeRepository;
import com.expensetracker.etrackerapi.repository.UserRepository;
import com.expensetracker.etrackerapi.service.implementation.IncomeServiceImpl;
import org.apache.catalina.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IncomeServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private IncomeRepository incomeRepository;

    @InjectMocks
    private IncomeServiceImpl incomeService;

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
    public void IncomeService_GetLogUser_ReturnUser(){
        //Arrange
        when(userRepository.findByUserEmail(email)).thenReturn(userModel);
        mockJwt(email);

        //Act
        UserModel user = incomeService.getLoggedInUser();
        //Assert
        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(userModel).isEqualTo(user);
    }

    @Test
    public void IncomeService_GetExpenseById_ReturnExpense(){
        when(userRepository.findByUserEmail(email)).thenReturn(userModel);
        mockJwt(email);

        UserModel user = incomeService.getLoggedInUser();

        IncomeModel income1 = IncomeModel.builder()
                .incomeCategory("Work")
                .incomeAmount(Long.valueOf(7000))
                .incomeDescription("Test")
                .build();
        IncomeModel income2 = IncomeModel.builder()
                .incomeCategory("Investment")
                .incomeAmount(Long.valueOf(8000))
                .incomeDescription("Test")
                .build();

        List<IncomeModel> incomes = Arrays.asList(income1,income2);

        when(incomeRepository.findByUser(user)).thenReturn(incomes);

        List<IncomeModel> result = incomeService.getIncomeByUserId();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.size()).isEqualTo(2);
    }

    @Test
    public void IncomeService_CreateExpense_ReturnExpense(){
        when(userRepository.findByUserEmail(email)).thenReturn(userModel);
        mockJwt(email);
        UserModel user = incomeService.getLoggedInUser();

        IncomeModel incomeModel = IncomeModel.builder()
                .incomeAmount(Long.valueOf(1004))
                .incomeCategory("Test")
                .incomeDescription("Test")
                .build();

        when(incomeService.getLoggedInUser()).thenReturn(userModel);
        when(incomeRepository.save(incomeModel)).thenReturn(incomeModel);

        IncomeModel saveExpense = incomeService.createIncome(incomeModel);

        Assertions.assertThat(saveExpense).isNotNull();
        Assertions.assertThat(saveExpense.getUser()).isEqualTo(userModel);
    }

    @Test
    public void  IncomeService_UpdateExpense_ReturnExpense(){
        Long expenseId = 1L;
        userModel.setuId(1L);

        when(userRepository.findByUserEmail(email)).thenReturn(userModel);
        mockJwt(email);
        UserModel user = incomeService.getLoggedInUser();

        IncomeModel existingIncome = IncomeModel.builder()
                .incomeCategory("Original")
                .incomeAmount(Long.valueOf(6000))
                .incomeDescription("Original")
                .user(userModel)
                .build();

        when(incomeRepository.findById(expenseId)).thenReturn(Optional.of(existingIncome));

        IncomeModel updatedIncome = IncomeModel.builder()
                .incomeCategory("Test")
                .incomeAmount(Long.valueOf(6000))
                .incomeDescription("Test")
                .build();

        when(incomeRepository.save(existingIncome)).thenReturn(existingIncome);

        IncomeModel result = incomeService.updateIncome(updatedIncome,expenseId);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getIncomeCategory()).isEqualTo("Test");
        Assertions.assertThat(result.getIncomeDescription()).isEqualTo("Test");
    }

    @Test
    public void IncomeService_DeleteExpense_ReturnExpense(){
        Long incomeId = 1L;
        IncomeModel incomeModel = IncomeModel.builder()
                .incomeCategory("Test")
                .incomeAmount(Long.valueOf(6000))
                .incomeDescription("Test")
                .build();

        when(incomeRepository.findById(incomeId)).thenReturn(Optional.of(incomeModel));
        incomeService.deleteIncome(incomeId);
        when(incomeRepository.findById(incomeId)).thenReturn(Optional.empty());

        Optional<IncomeModel> result = incomeRepository.findById(incomeId);
        Assertions.assertThat(result).isEmpty();
    }
}
