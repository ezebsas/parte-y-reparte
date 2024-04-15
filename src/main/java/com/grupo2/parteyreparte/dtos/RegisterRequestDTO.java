package com.grupo2.parteyreparte.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestDTO {
    private String name;
    private int age;
    private String email;
    private String password;

    public RegisterRequestDTO(String name, int age, String email, String password) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
    }
}
