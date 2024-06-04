package com.grupo2.parteyreparte.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Schema(description = "DTO that send the user to register his account")
public class RegisterRequestDTO {

    @Schema(name = "name", description = "User's name", example = "Miguel", type = "string")
    private String name;

    @Schema(name = "age", description = "User's age", example = "19", type = "int")
    private int age;

    @Schema(name = "email", description = "User's email, it'll be the username", example = "example@example", type = "string")
    private String email;

    @Schema(name = "password", description = "User's password. It must have at least one uppercase and a number", example = "A1as2Dd", type = "string")
    private String password;

    public RegisterRequestDTO(String name, int age, String email, String password) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
    }
}
