package com.grupo2.parteyreparte.services;

import com.grupo2.parteyreparte.exceptions.AuthException;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PasswordValidator {

    private final Pattern pattern;
    private final String regex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{5,}$";

    public PasswordValidator() {
        this.pattern = Pattern.compile(regex);
    }

    /***
     *
     * @param password
     * @throws AuthException when the password doesn't have the minimum length of 5 or alphamumeric characters
     */
    public void validatePassword(String password) {

        Matcher matcher = pattern.matcher(password);

        if ( !matcher.matches()) {
            throw new AuthException("Password must have alphanumeric characters and a minimum length of 5");
        }
    }
}
