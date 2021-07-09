package com.kw.nodeimageeditorbackend.security;

import com.kw.nodeimageeditorbackend.filters.JwtTokenVerifier;
import com.kw.nodeimageeditorbackend.user.repositories.UserRepository;
import com.kw.nodeimageeditorbackend.user.services.UserRepositoryService;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.security.Key;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final DaoAuthenticationProvider authProvider;
    private final Key jwtKey;
    private final UserRepository userRepository;

    public SecurityConfig(
            @Value("${jwt.key}") String jwtKey,
            PasswordEncoder passwordEncoder,
            UserRepositoryService userDetailsService,
            UserRepository userRepository
    ) {
        this.userRepository = userRepository;
        this.authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder);
        authProvider.setUserDetailsService(userDetailsService);
        this.jwtKey = Keys.hmacShaKeyFor(jwtKey.getBytes());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new JwtTokenVerifier(userRepository, jwtKey), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/api/login", "/api/register").permitAll()
                .antMatchers("/api/user").authenticated()
                .antMatchers("/1").permitAll()
                .antMatchers("/2").authenticated()
                .anyRequest().permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(this.authProvider);
    }

    @Bean
    public AuthenticationManager getAuthenticationManager() throws Exception {
        return this.authenticationManager();
    }

    @Bean
    public Key jwtKey() {
        return jwtKey;
    }

}
