package com.kw.nodeimageeditorbackend.controlers;

import com.kw.nodeimageeditorbackend.entities.UserEntity;
import com.kw.nodeimageeditorbackend.entities.UserRoleEntity;
import com.kw.nodeimageeditorbackend.repositories.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5500")
@RequestMapping("/api")
public class UserController {
    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @CrossOrigin(origins = "http://localhost:5500")
    @PostMapping("/login")
    public void login() {
    }

    @GetMapping("/")
    public UserEntity getUser(@RequestParam Long id) {
        Optional<UserEntity> user = userRepository.findById(id);
        if(user.isPresent()) {
            List<UserRoleEntity> roles = user.get().getRoles();
            roles.forEach(userRole -> userRole.setUser(null));
            roles.forEach(r -> r.getRole().permissions.forEach(System.out::println));
            return user.get();
        } else {
            return null;
        }
    }

}
