package com.kw.nodeimageeditorbackend.user.services;

import com.kw.nodeimageeditorbackend.exceptions.authorization.BadCredentialsException;
import com.kw.nodeimageeditorbackend.security.ApplicationUserDetails;
import com.kw.nodeimageeditorbackend.user.requests.AuthenticationRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

@Service
public class JwtLoginService implements LoginService {
    private final Key jwtKey;
    private final Key jwtRefreshKey;
    private final AuthenticationManager authenticationManager;

    public JwtLoginService(@Value("${jwt.key}") String jwtSecret,
                           @Value("${jwt.refresh.key}") String jwtRefreshSecret,
                           AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        jwtKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        jwtRefreshKey = Keys.hmacShaKeyFor(jwtRefreshSecret.getBytes());
    }

    @Override
    public String createAuthenticationToken(AuthenticationRequest request) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        );

        Authentication auth;
        try {
            auth = authenticationManager.authenticate(authentication);
        } catch (AuthenticationException exception) {
            throw new BadCredentialsException("");
        }

        return createJwtToken(auth, jwtKey);
    }

    @Override
    public String createRefreshToken(AuthenticationRequest request) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        );

        Authentication auth = authenticationManager.authenticate(authentication);

        return createJwtToken(auth, jwtRefreshKey);
    }

    @Override
    public String refreshAuthorizationToken(String refreshToken) {
        Jws<Claims> claimsJws = Jwts
                .parserBuilder()
                .setSigningKey(jwtRefreshKey)
                .build()
                .parseClaimsJws(refreshToken);

        return Jwts.builder()
                .setClaims(claimsJws.getBody())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 1_000 * 60 * 60 * 48))
                .signWith(jwtKey)
                .compact();
    }

    private String createJwtToken(Authentication authentication, Key jwtKey) {
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
