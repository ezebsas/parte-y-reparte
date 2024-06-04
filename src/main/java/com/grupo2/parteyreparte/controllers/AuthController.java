package com.grupo2.parteyreparte.controllers;

import com.grupo2.parteyreparte.dtos.AuthRequestDTO;
import com.grupo2.parteyreparte.dtos.AuthResponseDTO;
import com.grupo2.parteyreparte.dtos.ErrorResponseDTO;
import com.grupo2.parteyreparte.dtos.RegisterRequestDTO;
import com.grupo2.parteyreparte.exceptions.AuthException;
import com.grupo2.parteyreparte.mappers.ApiResponse;
import com.grupo2.parteyreparte.models.User;
import com.grupo2.parteyreparte.services.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@CrossOrigin(value = "*")
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
@Tag(name = "Authentication")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> register(@RequestBody RegisterRequestDTO request) {

        ApiResponse<AuthResponseDTO> response = new ApiResponse<>();
        response.setMessage("User successfully registered ");
        response.setValue(authService.register(request));
        return ResponseEntity.created(URI.create("/user/me")).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> login(@RequestBody AuthRequestDTO request) {

        ApiResponse<AuthResponseDTO> response = new ApiResponse<>();
        response.setMessage("User logged in ");
        response.setValue(authService.login(request));
        return ResponseEntity.ok(response);
    }

}
