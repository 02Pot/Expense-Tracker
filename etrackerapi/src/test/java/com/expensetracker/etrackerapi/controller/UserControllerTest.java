package com.expensetracker.etrackerapi.controller;

import com.expensetracker.etrackerapi.model.UserModel;
import com.expensetracker.etrackerapi.service.implementation.JWTServiceImpl;
import com.expensetracker.etrackerapi.service.implementation.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JWTServiceImpl jwtService;

    private UserModel userModel;
    private UserModel userModel2;


    @BeforeEach
    public void init(){
        userModel = UserModel.builder()
                .userEmail("test@example.com")
                .uId(1L)
                .userFirstName("Test")
                .userLastName("User")
                .userPassword("password")
                .build();
        userModel2 = UserModel.builder()
                .userEmail("test@example.com")
                .uId(1L)
                .userFirstName("Test")
                .userLastName("User")
                .userPassword("test")
                .build();
    }

    @Test
    public void UserController_GetUser_ReturnUser() throws Exception{
        Long id = userModel.getuId();
        given(userService.getUser(id)).willReturn(userModel);

        ResultActions response = mockMvc.perform(get("/users/{id}",id)
                .accept(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void UserController_GetAllUser_ReturnAll() throws Exception{
        List<UserModel> userList = Arrays.asList(userModel,userModel2);
        given(userService.findAllUser()).willReturn(userList);

        ResultActions response = mockMvc.perform(get("/users/allUser")
                .accept(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void UserController_RegisterUser_ReturnRegistered() throws Exception{
        given(userService.register(ArgumentMatchers.any())).willAnswer(invocation -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userModel)));

        response.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void UserController_LoginUser_ReturnUser() throws Exception{
        String token = "jwtToken";
        given(userService.verify(any(UserModel.class))).willReturn(token);

        ResultActions response = mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userModel)))
                .andDo(print());

        response.andExpect(MockMvcResultMatchers.status().isOk());
        response.andExpect(MockMvcResultMatchers.jsonPath("$.token").value(token));

    }

    @Test
    public void UserController_UpdateUser_ReturnUpdated() throws Exception{
        Long id = userModel.getuId();
        given(userService.updateUser(id,userModel)).willAnswer(invocation -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(put("/users/{id}",id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userModel)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    public void UserController_DeleteUser_ReturnDeleted(){

    }

}
