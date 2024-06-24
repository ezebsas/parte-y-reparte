package com.grupo2.parteyreparte.services;

import com.grupo2.parteyreparte.dtos.AuthRequestDTO;
import com.grupo2.parteyreparte.dtos.AuthResponseDTO;
import com.grupo2.parteyreparte.dtos.RegisterRequestDTO;
import com.grupo2.parteyreparte.exceptions.AuthException;
import com.grupo2.parteyreparte.models.User;
import com.grupo2.parteyreparte.repositories.mongo.UserRepository;
import com.grupo2.parteyreparte.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordValidator passwordValidator;


    /***
     *
     * @param request that have the data of the user
     * @return AuthResponseDTO with the token if the register was success
     * @throws AuthException when the user already exist
     */
    public AuthResponseDTO register(RegisterRequestDTO request) {


        if (userRepository.findByEmailEquals(request.getEmail()).isPresent()) {
            throw new AuthException("User already exist");
        }

        passwordValidator.validatePassword(request.getPassword());

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

    /***
     *
     * @param request that have the data of the user
     * @return AuthResponseDTO with the token if the login was success
     * @throws AuthException when the user doesn't exist
     */
    public AuthResponseDTO login(AuthRequestDTO request) {

        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new AuthException("Wrong password or username");
        }


        User user = userRepository.findByEmailEquals(request.getEmail()).orElseThrow(() -> new AuthException("Username doesn't exist"));

        String jwtToken = jwtService.generateToken(user);

        return new AuthResponseDTO(jwtToken);
    }

}
