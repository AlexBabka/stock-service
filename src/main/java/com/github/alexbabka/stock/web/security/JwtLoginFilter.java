package com.github.alexbabka.stock.web.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * Filter used to authenticate user and generate JWT token.
 */
@Component
public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {
    private JwtTokenProvider jwtTokenProvider;

    public JwtLoginFilter(JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher("/token"));

        this.jwtTokenProvider = jwtTokenProvider;
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException, IOException, ServletException {
        ApiCredentials credentials = new ObjectMapper()
                .readValue(req.getInputStream(), ApiCredentials.class);

        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        credentials.getUsername(),
                        credentials.getPassword(),
                        Collections.emptyList()
                )
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        String token = jwtTokenProvider.createToken(auth.getName());

        res.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }
}
