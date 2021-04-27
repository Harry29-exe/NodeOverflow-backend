package com.kw.nodeimageeditorbackend.controlers;

import com.kw.nodeimageeditorbackend.services.LoginService;
import com.kw.nodeimageeditorbackend.services.UserService;
import com.kw.nodeimageeditorbackend.request.AuthenticationRequest;
import com.kw.nodeimageeditorbackend.request.CreateUserRequest;
import com.kw.nodeimageeditorbackend.request.DeleteUserRequest;
import com.kw.nodeimageeditorbackend.request.UpdateUserDetailsRequest;
import com.kw.nodeimageeditorbackend.security.ApplicationUserDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import static org.springframework.http.HttpStatus.*;

import javax.naming.AuthenticationException;
import javax.naming.directory.InvalidAttributeValueException;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;

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

    @PostMapping("/login")
    public void login(@RequestBody AuthenticationRequest request, HttpServletResponse response) {
        String token = loginService.createAuthenticationToken(request);
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("Access-Control-Expose-Headers", "Authorization");
    }

    @PostMapping("/token-refresh")
    public void refresh(@RequestHeader String authentication) {

    }

    @PostMapping("/register")
    public void createUser(@RequestBody CreateUserRequest request) {
        userService.createUser(request);
    }

    @DeleteMapping("/user")
    public void deleteUser(@RequestBody DeleteUserRequest request, ResponseBody responseBody) {
        userService.deleteUser(request);
    }

    @PatchMapping("/user")
    public void patchUser(@RequestBody UpdateUserDetailsRequest request) {
        System.out.println("we");
        userService.updateUser(request);
    }
}
