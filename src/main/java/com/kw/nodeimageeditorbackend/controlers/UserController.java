package com.kw.nodeimageeditorbackend.controlers;

import com.kw.nodeimageeditorbackend.repositories.UserService;
import com.kw.nodeimageeditorbackend.request.CreateUserRequest;
import com.kw.nodeimageeditorbackend.request.DeleteUserRequest;
import com.kw.nodeimageeditorbackend.security.UserPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import static org.springframework.http.HttpStatus.*;

import javax.naming.directory.InvalidAttributeValueException;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@RestController
@CrossOrigin(origins = "http://localhost:5500")
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @CrossOrigin(origins = "http://localhost:5500")
    @PostMapping("/login")
    public void login() {
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
    public ResponseEntity deleteUser(@RequestBody DeleteUserRequest request) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
}
