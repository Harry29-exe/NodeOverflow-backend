package com.kw.nodeimageeditorbackend.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kw.nodeimageeditorbackend.request.AuthenticationRequest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.Date;
import java.time.LocalDate;
import java.time.temporal.TemporalUnit;

public class JwtFilter extends AbstractAuthenticationProcessingFilter {
    private AuthenticationManager authenticationManager;
    private final Key key = Keys.hmacShaKeyFor("gjfdskoghdflkifsdghblfkdjgnbvlkdjshnbvgfkd".getBytes());

    public JwtFilter(AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher("/api/login", "POST"));
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            AuthenticationRequest authReq = new ObjectMapper().readValue(request.getInputStream(), AuthenticationRequest.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authReq.getUsername(),
                    authReq.getPassword()
            );
            return authenticationManager.authenticate(authentication);

        } catch (Exception ex) {
            throw new RuntimeException("Failed to authenticate");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 1_000*60*3))
                .signWith(key)
                .compact();
        response.addHeader("Authorization", "Bearer " + token);
    }
}
