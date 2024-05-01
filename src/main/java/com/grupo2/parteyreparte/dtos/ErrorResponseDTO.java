package com.grupo2.parteyreparte.dtos;

import lombok.Getter;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class ErrorResponseDTO {
    private final LocalDateTime timestamp;
    private final int status;
    private final String error;

    public ErrorResponseDTO(int status, String error) {
        this.timestamp = LocalDateTime.now();;
        this.status = status;
        this.error = error;
    }
}
