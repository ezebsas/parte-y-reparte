package com.grupo2.parteyreparte.controllers;

import com.grupo2.parteyreparte.dtos.AuthRequestDTO;
import com.grupo2.parteyreparte.dtos.AuthResponseDTO;
import com.grupo2.parteyreparte.dtos.RegisterRequestDTO;
import com.grupo2.parteyreparte.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterRequestDTO request) {

        return ResponseEntity.ok(authService.register(request));
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
}
