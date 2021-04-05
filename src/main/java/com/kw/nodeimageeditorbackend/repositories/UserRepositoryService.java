package com.kw.nodeimageeditorbackend.repositories;

import com.kw.nodeimageeditorbackend.entities.UserEntity;
import com.kw.nodeimageeditorbackend.request.CreateUserRequest;
import com.kw.nodeimageeditorbackend.security.UserPrincipal;
import com.kw.nodeimageeditorbackend.security.UserRole;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRepositoryService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserRepositoryService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if(user.isEmpty()) {
            user = userRepository.findByEmail(username);
        }

        if(user.isEmpty()) {
            throw new UsernameNotFoundException("Username not found");
        } else {
            return new UserPrincipal(user.get());
        }
    }
}
