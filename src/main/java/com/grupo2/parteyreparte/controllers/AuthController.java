package com.grupo2.parteyreparte.controllers;

import com.grupo2.parteyreparte.dtos.AuthRequestDTO;
import com.grupo2.parteyreparte.dtos.AuthResponseDTO;
import com.grupo2.parteyreparte.dtos.ErrorResponseDTO;
import com.grupo2.parteyreparte.dtos.RegisterRequestDTO;
import com.grupo2.parteyreparte.exceptions.AuthException;
import com.grupo2.parteyreparte.models.User;
import com.grupo2.parteyreparte.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterRequestDTO request) {
        return ResponseEntity.created(URI.create("/user/me")).body(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        //TODO
        // Pensar alguna manera de poner fin al token
        // Ej black list pero preguntar
        return null;
    }


    @ExceptionHandler(value = AuthException.class)
    public ResponseEntity<ErrorResponseDTO> authExceptionResolver(AuthException e) {
        return ResponseEntity.badRequest().body(new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }
}
