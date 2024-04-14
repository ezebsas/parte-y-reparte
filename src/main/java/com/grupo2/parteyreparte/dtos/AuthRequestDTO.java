package com.grupo2.parteyreparte.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequestDTO {
    String email;
    String password;

    public AuthRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
