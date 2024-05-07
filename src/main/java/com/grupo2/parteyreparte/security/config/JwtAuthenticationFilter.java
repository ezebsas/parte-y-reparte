package com.grupo2.parteyreparte.security.config;

import com.grupo2.parteyreparte.security.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String username;

        if (authHeader == null || !authHeader.startsWith("Bearer ") || request.getRequestURI().contains("/api/v1/auth/")) {
            filterChain.doFilter(request,response);
            return;
        }
        /*
        * The JWT arrives through the header, we extract the user to be able to subsequently
        * load the user to the Principal who has both the authorizations and user data (although in this case there is only one role, the user).
        * Since the system does not save user data between requests, we must load the principal in each request.
        * Advantages: Our system is stateless, therefore it allows us to scale horizontally
        * Disadvantages: If the user "is too heavy", it would affect the performance of our system
        * Possible solution: Only load key data in the main one, making it take up the least amount of space
        * */

        /* We get the jwt token from the header */
        jwtToken = authHeader.substring(7);

        /* extract of the username from the jwt. If the jwt wasn't signed by us, it will throw an exception*/
        username = jwtService.extractUsername(jwtToken);

        /* if the authentication is null means that the user isn't logged. It will pass all the time because
        * we have a stateless Spring config. Spring won't save a session
        * */
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            /* We got the user info from the db to load the Principal with the user data*/
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            /* Verify if the token expires */
            if (jwtService.isTokenValid(jwtToken)) {
                UsernamePasswordAuthenticationToken authToken
                        = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);

            }
        }

        filterChain.doFilter(request,response);
    }
}
