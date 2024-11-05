package com.expensetracker.etrackerapi.mapper;

import com.expensetracker.etrackerapi.dto.UserDTO;
import com.expensetracker.etrackerapi.model.UserModel;

public class UserMapper {
    
    public static UserDTO mapToUserDTO(UserModel userModel) {
        return new UserDTO(
            userModel.getuId(),
            userModel.getUserEmail(),
            userModel.getuserFirstName(),
            userModel.getuserLastName(),
            userModel.getUserNumber(),
            userModel.getCreatedOn()
        );
    }

    public static UserModel mapToUser(UserDTO userDTO){
        return new UserModel(
            userDTO.getUserId(),
            userDTO.getUserEmail(),
            userDTO.getUserFirstName(),
            userDTO.getUserLastName(),
            userDTO.getUserNumber(),
            userDTO.getCreatedOn()
        );
    }
}
