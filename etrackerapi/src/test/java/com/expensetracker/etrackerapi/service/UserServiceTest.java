package com.expensetracker.etrackerapi.service;

import com.expensetracker.etrackerapi.model.UserModel;
import com.expensetracker.etrackerapi.repository.UserRepository;
import com.expensetracker.etrackerapi.service.implementation.JWTServiceImpl;
import com.expensetracker.etrackerapi.service.implementation.UserServiceImpl;
import org.apache.catalina.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JWTServiceImpl jwtService;

    private UserModel userModel;

    @BeforeEach
    public void init(){
        userModel = UserModel.builder()
                .userEmail("testing@gmail.com")
                .userFirstName("te")
                .userLastName("st")
                .userNumber(25)
                .userPassword("test")
                .uId(1L)
                .build();
    }

    @Test
    public void UserService_RegisterUser_ReturnUser(){
        //Arrange
        when(passwordEncoder.encode("test")).thenReturn("encodedPass");
        when(userRepository.save(Mockito.any(UserModel.class))).thenAnswer(invocation -> {
           UserModel savedUser = invocation.getArgument(0);
           savedUser.setUserPassword("encodedPass");
           return  savedUser;
        });
        //Act
        UserModel savedUser = userService.register(userModel);
        //Assert
        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getUserPassword()).isEqualTo("encodedPass");

    }

    @Test
    public  void UserService_VerifyUser_ReturnUser(){
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        when(authentication.isAuthenticated()).thenReturn(true);

        String expecttoken = "jwtToken";
        when(jwtService.generateToken(userModel.getUserEmail())).thenReturn(expecttoken);

        String token = userService.verify(userModel);

        Assertions.assertThat(expecttoken).isNotNull();
        Assertions.assertThat(expecttoken).isEqualTo(token);

    }

    @Test
    public  void UserService_UpdateUser_ReturnUpdatedUser(){
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.of(userModel));

        UserModel updatedUser = UserModel.builder()
                .userEmail("newtest@gmail.com")
                .userFirstName("te")
                .userLastName("st")
                .userNumber(25)
                .userPassword("test2")
                .build();

        when(userRepository.save(any(UserModel.class))).thenReturn(updatedUser);

        UserModel result = userService.updateUser(userId, updatedUser);


        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(updatedUser.getUserEmail()).isEqualTo(result.getUserEmail());
        Assertions.assertThat(updatedUser.getUserPassword()).isEqualTo(result.getUserPassword());
    }

    @Test
    public  void UserService_DeleteUser_ReturnDeletedUser(){
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.of(userModel));
        userService.deleteUser(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Optional<UserModel> result = userRepository.findById(userId);
        Assertions.assertThat(result).isEmpty();

    }

    @Test
    public  void  UserService_FindAllUser_ReturnAllUser(){
        UserModel userModel1 = UserModel.builder()
                .userEmail("testing1@gmail.com")
                .userFirstName("te1")
                .userLastName("st1")
                .userNumber(1)
                .userPassword("test1")
                .build();
        UserModel userModel2 = UserModel.builder()
                .userEmail("testing2@gmail.com")
                .userFirstName("te2")
                .userLastName("st2")
                .userNumber(2)
                .userPassword("test2")
                .build();

        List<UserModel> allUsers = List.of(userModel1, userModel2);

        when(userRepository.findAll()).thenReturn(allUsers);

        List<UserModel> users = userService.findAllUser();

        Assertions.assertThat(users).isNotNull();
        Assertions.assertThat(users.size()).isEqualTo(2);
        Assertions.assertThat(allUsers).isEqualTo(users);

    }
}
