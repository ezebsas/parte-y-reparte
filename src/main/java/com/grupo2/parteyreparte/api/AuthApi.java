package com.grupo2.parteyreparte.api;

import com.grupo2.parteyreparte.dtos.AuthRequestDTO;
import com.grupo2.parteyreparte.dtos.AuthResponseDTO;
import com.grupo2.parteyreparte.dtos.RegisterRequestDTO;
import com.grupo2.parteyreparte.mappers.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Authentication")
public interface AuthApi {
    @Operation(summary = "Create a new account", description = "Create a new account by name, email, age and password. Password must to have a Uppercase and a number")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> register(@RequestBody RegisterRequestDTO request);
    @Operation(summary = "Log in an account", description = "Login by email and password.")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> login(@RequestBody AuthRequestDTO request);
}
