package com.expensetracker.etrackerapi.repository;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.expensetracker.etrackerapi.model.UserModel;

@DataJpaTest
@AutoConfigureTestDatabase(connection=EmbeddedDatabaseConnection.H2)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void UserRepository_SaveAll_ReturnSavedUser(){
        //Arrange
        UserModel userModel = UserModel.builder()
            .userEmail("testing@gmail.com")
            .userFirstName("te")
            .userLastName("st")
            .userNumber(25)
            .userPassword("test")
            .build();

        //Act
        UserModel savedUserModel = userRepository.save(userModel);

        //Assert
        Assertions.assertThat(savedUserModel).isNotNull();
        Assertions.assertThat(savedUserModel.getuId()).isGreaterThan(0);
    }

    @Test
    public void UserRepository_GetAll_ReturnsManyUser(){
        UserModel userModel1 = UserModel.builder()
            .userEmail("testing2@gmail.com")
            .userFirstName("te")
            .userLastName("st")
            .userNumber(251224)
            .userPassword("test")
            .build();
        UserModel userModel2 = UserModel.builder()
            .userEmail("testing3@gmail.com")
            .userFirstName("te")
            .userLastName("st")
            .userNumber(232)
            .userPassword("test")
            .build();

        userRepository.save(userModel1);
        userRepository.save(userModel2);

        List<UserModel> userList = userRepository.findAll();

        Assertions.assertThat(userList).isNotNull();
        Assertions.assertThat(userList).doesNotHaveDuplicates();
        Assertions.assertThat(userList.size()).isEqualTo(2);
    }

    @Test
    public void UserRepository_FindById_ReturnsMoreThanOneUser(){
        UserModel userModel3 = UserModel.builder()
        .userEmail("testing3@gmail.com")
        .userFirstName("te")
        .userLastName("st")
        .userNumber(232)
        .userPassword("test")
        .build();

        userRepository.save(userModel3);
        UserModel userReturn = userRepository.findById(userModel3.getuId()).get();

        Assertions.assertThat(userReturn).isNotNull();
        Assertions.assertThat(userReturn).hasNoNullFieldsOrProperties();
    }

    @Test
    public void UserRepository_UpdateUser_ReturnUpdatedUser() {
        UserModel userModel3 = UserModel.builder()
        .userEmail("testing3@gmail.com")
        .userFirstName("te")
        .userLastName("st")
        .userNumber(232)
        .userPassword("test")
        .build();
        
        userRepository.save(userModel3);

        UserModel updatedUserFromDb = userRepository.findByUserEmail(userModel3.getUserEmail());
        updatedUserFromDb.setuserFirstName("kier");
        updatedUserFromDb.setuserLastName("ibay");

        Assertions.assertThat(updatedUserFromDb.getuserFirstName()).isNotNull();
        Assertions.assertThat(updatedUserFromDb.getuserLastName()).isNotNull();
    }

    @Test
    public void UserRepository_ExistById_ReturnBoolean(){
        UserModel userModel3 = UserModel.builder()
        .userEmail("testing3@gmail.com")
        .userFirstName("te")
        .userLastName("st")
        .userNumber(232)
        .userPassword("test")
        .build();

        String existingUser = userModel3.getUserEmail();

        boolean exists = userRepository.existsByUserEmail(existingUser);

        Assertions.assertThat(exists).isTrue();
    }

    @Test
    public void UserRepository_DeleteUser_ReturnDelete(){
        UserModel userModel3 = UserModel.builder()
        .userEmail("testing3@gmail.com")
        .userFirstName("te")
        .userLastName("st")
        .userNumber(232)
        .userPassword("test")
        .build();

        userRepository.save(userModel3);
        userRepository.deleteById(userModel3.getuId());
        Optional<UserModel> userReturn = userRepository.findById(userModel3.getuId());

        Assertions.assertThat(userReturn).isEmpty();
    }
}
