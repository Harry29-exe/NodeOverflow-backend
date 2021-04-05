package com.kw.nodeimageeditorbackend.controlers;

import com.kw.nodeimageeditorbackend.entities.User;
import com.kw.nodeimageeditorbackend.repositories.UserRepository;
import org.springframework.web.bind.annotation.*;

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
    public User getUser(@RequestParam Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            user.get().getRoles().forEach(userRole -> userRole.setUser(null));
            return user.get();
        } else {
            return null;
        }
    }

}
