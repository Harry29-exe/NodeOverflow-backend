package com.kw.nodeimageeditorbackend.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kw.nodeimageeditorbackend.request.AuthenticationRequest;
import com.kw.nodeimageeditorbackend.security.UserPrincipal;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;

public class JwtAuthentication extends AbstractAuthenticationProcessingFilter {
    private final AuthenticationManager authenticationManager;
    private final Key key = Keys.hmacShaKeyFor("gjfdskoghdflkifsdghblfkdjgnbvlkdjshnbvgfkd".getBytes());

    public JwtAuthentication(AuthenticationManager authenticationManager) {
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
            throw new AuthenticationException("Failed to authenticate") {
            };
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        UserPrincipal user = (UserPrincipal) authResult.getPrincipal();
        HashMap<String, String> claims = new HashMap<>();
        claims.put("email", user.getEmail());
        claims.put("sub", user.getUsername());
        claims.put("id", user.getId().toString());

        String token = Jwts.builder()
                .setClaims(claims)
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 1_000 * 60 * 3))
                .signWith(key)
                .compact();
        response.addHeader("Authorization", "Bearer " + token);
    }
}
