package com.kw.nodeimageeditorbackend.services;

import com.kw.nodeimageeditorbackend.request.AuthenticationRequest;
import com.kw.nodeimageeditorbackend.security.ApplicationUserDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

@Service
public class JwtLoginService implements LoginService {

    private final Key jwtKey;
    private final AuthenticationManager authenticationManager;

    public JwtLoginService(@Value("${jwt.key}") String jwtSecret, AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        jwtKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    @Override
    public String createAuthenticationToken(AuthenticationRequest request) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        );

        Authentication auth = authenticationManager.authenticate(authentication);

        return createJwtToken(auth);
    }

    private String createJwtToken(Authentication authentication) {
        System.out.println(authentication.getClass());
        ApplicationUserDetails user = (ApplicationUserDetails) authentication.getPrincipal();
        HashMap<String, String> claims = new HashMap<>();
        claims.put("email", user.getEmail());
        claims.put("sub", user.getUsername());
        claims.put("id", user.getId().toString());

        return Jwts.builder()
                .setClaims(claims)
                .claim("authorities", authentication.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 1_000 * 60 * 30))
                .signWith(jwtKey)
                .compact();
    }
}
