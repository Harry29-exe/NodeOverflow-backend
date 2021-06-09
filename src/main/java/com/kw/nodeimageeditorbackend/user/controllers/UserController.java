package com.kw.nodeimageeditorbackend.user.controllers;

import com.kw.nodeimageeditorbackend.user.requests.AuthenticationRequest;
import com.kw.nodeimageeditorbackend.user.requests.CreateUserRequest;
import com.kw.nodeimageeditorbackend.user.requests.DeleteUserRequest;
import com.kw.nodeimageeditorbackend.user.requests.UpdateUserDetailsRequest;
import com.kw.nodeimageeditorbackend.user.services.LoginService;
import com.kw.nodeimageeditorbackend.user.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.util.Arrays;
import java.util.stream.Collectors;

import static com.kw.nodeimageeditorbackend.configuration.CorsAddresses.*;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final LoginService loginService;

    public UserController(UserService userService, LoginService loginService) {
        this.userService = userService;
        this.loginService = loginService;
    }

    @CrossOrigin(value = {FRONTEND_DEV, FRONTEND_PROD}, allowCredentials = "true", exposedHeaders = {"Authorization"})
    @PostMapping("/login")
    public void login(@RequestBody @Valid AuthenticationRequest request, HttpServletResponse response) {
        String token = loginService.createAuthenticationToken(request);
        String refreshToken = loginService.createRefreshToken(request);
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("Set-Cookie", "token=" + refreshToken + "; Max-Age=7200; Path=/");
    }

    @CrossOrigin(value = {FRONTEND_DEV, FRONTEND_PROD}, allowCredentials = "true", exposedHeaders = {"Authorization"})
    @GetMapping("/token-refresh")
    public void refresh(@CookieValue(value = "token") String refreshToken, HttpServletResponse response) {
        String refreshedToken = loginService.refreshAuthorizationToken(refreshToken);
        response.addHeader("Authorization", "Bearer " + refreshedToken);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody @Valid CreateUserRequest request) {
        userService.createUser(request);
    }

    @DeleteMapping("/user")
    public void deleteUser(@RequestBody @Valid DeleteUserRequest request) {
        userService.deleteUser(request);
    }

    @PatchMapping("/user")
    public void patchUser(@RequestBody @Valid UpdateUserDetailsRequest request) {
        System.out.println("we");
        userService.updateUser(request);
    }
}
