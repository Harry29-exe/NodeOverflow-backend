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
    }

    @PostMapping("/token-refresh")
    public void refresh(@RequestHeader String authentication) {

    }

    @PostMapping("/register")
    public ResponseEntity createUser(@RequestBody CreateUserRequest request) {
        try {
            userService.createUser(request);
        } catch (EntityExistsException ex) {
            return ResponseEntity.status(CONFLICT).build();
        } catch (InvalidAttributeValueException ex) {
            return ResponseEntity.status(BAD_REQUEST).header("Warning", ex.getMessage()).build();
        }

        return ResponseEntity.status(CREATED).build();
    }

    @DeleteMapping("/user")
    public ResponseEntity deleteUser(@RequestBody DeleteUserRequest request, ResponseBody responseBody) {
        ApplicationUserDetails user = (ApplicationUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(request.getUsernameOrEmail().contains("@")) {
            if(!user.getEmail().equals(request.getUsernameOrEmail())) {
                return ResponseEntity.status(UNAUTHORIZED).build();
            }
        } else if (!user.getUsername().equals(request.getUsernameOrEmail())) {
            return ResponseEntity.status(UNAUTHORIZED).build();
        }

        try {
            userService.deleteUser(request);
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(UNAUTHORIZED).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(EXPECTATION_FAILED).build();
        }

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/user")
    public ResponseEntity patchUser(@RequestBody UpdateUserDetailsRequest request) throws AuthenticationException {
        userService.updateUser(request);
        return ResponseEntity.ok().build();
    }
}
