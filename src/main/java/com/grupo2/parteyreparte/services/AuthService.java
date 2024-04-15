package com.grupo2.parteyreparte.services;

import com.grupo2.parteyreparte.dtos.AuthRequestDTO;
import com.grupo2.parteyreparte.dtos.AuthResponseDTO;
import com.grupo2.parteyreparte.dtos.RegisterRequestDTO;
import com.grupo2.parteyreparte.exceptions.EntityNotFoundException;
import com.grupo2.parteyreparte.models.User;
import com.grupo2.parteyreparte.repositories.UserRepository;
import com.grupo2.parteyreparte.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthResponseDTO register(RegisterRequestDTO request) {

        User user = User.builder()
                .name(request.getName())
                .age(request.getAge())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                        .productsPublished(new ArrayList<>())
                        .productsSubscribed(new ArrayList<>())
                        .notifications(new ArrayList<>())
                        .build();

        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);

        return new AuthResponseDTO(jwtToken);
    }

    public AuthResponseDTO login(AuthRequestDTO request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByUsername(request.getEmail()).orElseThrow(() -> new EntityNotFoundException("Username or password wrong"));

        String jwtToken = jwtService.generateToken(user);

        return new AuthResponseDTO(jwtToken);
    }
}
