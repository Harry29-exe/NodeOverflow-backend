package com.kw.nodeimageeditorbackend.controlers;

import com.kw.nodeimageeditorbackend.repositories.UserService;
import com.kw.nodeimageeditorbackend.request.CreateUserRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.directory.InvalidAttributeValueException;
import javax.persistence.EntityExistsException;

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

//    @GetMapping("/")
//    public UserEntity getUser(@RequestParam Long id) {
//        Optional<UserEntity> user = userService.findById(id);
//        if (user.isPresent()) {
//            List<UserRoleEntity> roles = user.get().getRoles();
//            roles.forEach(userRole -> userRole.setUser(null));
//            roles.forEach(r -> r.getRole().permissions.forEach(System.out::println));
//            return user.get();
//        } else {
//            return null;
//        }
//    }

    @PostMapping("/register")
    public ResponseEntity createUser(@RequestBody CreateUserRequest request) {
        try {
            userService.createUser(request);
        } catch (EntityExistsException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (InvalidAttributeValueException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Warning", ex.getMessage()).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
