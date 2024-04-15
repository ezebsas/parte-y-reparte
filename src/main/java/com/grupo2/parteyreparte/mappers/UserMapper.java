package com.grupo2.parteyreparte.mappers;

import com.grupo2.parteyreparte.dtos.UserDTO;
import com.grupo2.parteyreparte.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO mapToUserDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setAge(user.getAge());
        return userDTO;
    }
}
