package com.grupo2.parteyreparte.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Schema(description = "DTO for login response")
public class AuthResponseDTO {

    @Schema(name = "token", description = "The token JWT, result of the login", type = "string")
    private String token;

    public AuthResponseDTO(String token) {
        this.token = token;
    }
}
