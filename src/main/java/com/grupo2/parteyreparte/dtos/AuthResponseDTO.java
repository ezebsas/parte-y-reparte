package com.grupo2.parteyreparte.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class AuthResponseDTO {
    private String token;

    public AuthResponseDTO(String token) {
        this.token = token;
    }
}
