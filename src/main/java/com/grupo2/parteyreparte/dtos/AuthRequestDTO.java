package com.grupo2.parteyreparte.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Schema(description = "DTO for login request")
public class AuthRequestDTO {

    @Schema(name = "email", description = "User's email, it's the username", example = "Harry@example", type = "string")
    String email;

    @Schema(name = "password", description = "User's password", example = "A1as2Dd", type = "string")
    String password;

    public AuthRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
