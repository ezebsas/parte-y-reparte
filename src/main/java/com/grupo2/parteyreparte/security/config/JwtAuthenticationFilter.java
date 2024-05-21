package com.grupo2.parteyreparte.security.config;

import com.grupo2.parteyreparte.security.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String userId;

        if (authHeader == null || !authHeader.startsWith("Bearer ") || request.getRequestURI().contains("/api/v1/auth/")) {
            filterChain.doFilter(request,response);
            return;
        }

        jwtToken = authHeader.substring(7);

        userId = jwtService.extractSubject(jwtToken);

        if(jwtService.isTokenValid(jwtToken)) {

            UsernamePasswordAuthenticationToken authToken
                    = new UsernamePasswordAuthenticationToken(userId,null, List.of(new SimpleGrantedAuthority("USER")));

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);

        }

        filterChain.doFilter(request,response);
    }
}
