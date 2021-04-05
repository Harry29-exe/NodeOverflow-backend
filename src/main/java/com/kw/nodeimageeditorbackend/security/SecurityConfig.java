package com.kw.nodeimageeditorbackend.security;

import com.kw.nodeimageeditorbackend.filters.JwtAuthentication;
import com.kw.nodeimageeditorbackend.filters.JwtTokenVerifier;
import com.kw.nodeimageeditorbackend.repositories.UserRepositoryService;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final DaoAuthenticationProvider authProvider;

    public SecurityConfig(PasswordEncoder passwordEncoder, UserRepositoryService userDetailsService) {
        this.authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder);
        authProvider.setUserDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("Auth manager" + this.authenticationManager());
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new JwtAuthentication(authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new JwtTokenVerifier(), JwtAuthentication.class)
                .authorizeRequests()
                .antMatchers("/api/login").permitAll()
                .antMatchers("/1").permitAll()
                .antMatchers("/2").authenticated()
                .anyRequest().permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(this.authProvider);
    }

}
