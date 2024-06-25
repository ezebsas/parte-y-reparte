package com.grupo2.parteyreparte.models;

import com.grupo2.parteyreparte.dtos.UserDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test
    void updateUserSuccessfully() {
        User user = new User("Migue",9,"m@m");
        UserDTO newNameAndAge = new UserDTO();
        newNameAndAge.setName("Miguelito");
        newNameAndAge.setAge(29);

        user.updateUser(newNameAndAge);

        assertEquals(newNameAndAge.getName(),user.getName());
        assertEquals(newNameAndAge.getAge(),user.getAge());
    }
}