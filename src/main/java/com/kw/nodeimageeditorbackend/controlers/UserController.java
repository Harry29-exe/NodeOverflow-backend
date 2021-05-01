package com.kw.nodeimageeditorbackend.controlers;

import com.kw.nodeimageeditorbackend.request.user.AuthenticationRequest;
import com.kw.nodeimageeditorbackend.request.user.CreateUserRequest;
import com.kw.nodeimageeditorbackend.request.user.DeleteUserRequest;
import com.kw.nodeimageeditorbackend.request.user.UpdateUserDetailsRequest;
import com.kw.nodeimageeditorbackend.services.user.LoginService;
import com.kw.nodeimageeditorbackend.services.user.UserService;
import org.springframework.http.HttpStatus;
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
    public void login(@RequestBody @Valid AuthenticationRequest request, HttpServletResponse response) {
        String token = loginService.createAuthenticationToken(request);
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("Access-Control-Expose-Headers", "Authorization");
    }

    //TODO
    @PostMapping("/token-refresh")
    public void refresh(@CookieValue(value = "token") String refreshToken,
                        @RequestHeader String authentication) {

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
