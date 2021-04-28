package com.kw.nodeimageeditorbackend.controlers;

import com.kw.nodeimageeditorbackend.request.AuthenticationRequest;
import com.kw.nodeimageeditorbackend.request.CreateUserRequest;
import com.kw.nodeimageeditorbackend.request.DeleteUserRequest;
import com.kw.nodeimageeditorbackend.request.UpdateUserDetailsRequest;
import com.kw.nodeimageeditorbackend.services.LoginService;
import com.kw.nodeimageeditorbackend.services.UserService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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
    public void refresh(@CookieValue(value = "token") String refreshToken,
                        @RequestHeader String authentication) {

    }

    @PostMapping("/register")
    public void createUser(@RequestBody @Valid CreateUserRequest request) {
        userService.createUser(request);
    }

    @DeleteMapping("/user")
    public void deleteUser(@RequestBody DeleteUserRequest request) {
        userService.deleteUser(request);
    }

    @PatchMapping("/user")
    public void patchUser(@RequestBody UpdateUserDetailsRequest request) {
        System.out.println("we");
        userService.updateUser(request);
    }
}
