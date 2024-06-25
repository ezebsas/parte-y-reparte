package com.grupo2.parteyreparte.services;

import com.grupo2.parteyreparte.exceptions.AuthException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PasswordValidatorTest {

    @Autowired
    PasswordValidator passwordValidator;

    @Test
    void isAInvalidPassword() {
        String aPassword = "1111";

        assertThrows(AuthException.class,() -> passwordValidator.validatePassword(aPassword));
    }

    @Test
    void isAValidPassword() {
        String aPassword = "1111A";

        assertDoesNotThrow(() -> passwordValidator.validatePassword(aPassword));
    }

}