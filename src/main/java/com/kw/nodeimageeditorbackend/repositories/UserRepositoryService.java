package com.kw.nodeimageeditorbackend.repositories;

import com.kw.nodeimageeditorbackend.entities.UserEntity;
import com.kw.nodeimageeditorbackend.entities.UserRoleEntity;
import com.kw.nodeimageeditorbackend.request.CreateUserRequest;
import com.kw.nodeimageeditorbackend.request.DeleteUserRequest;
import com.kw.nodeimageeditorbackend.request.UpdateUserDetailsRequest;
import com.kw.nodeimageeditorbackend.security.ApplicationUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import javax.naming.directory.InvalidAttributeValueException;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.*;

import static com.kw.nodeimageeditorbackend.security.UserRole.USER;

@Service
public class UserRepositoryService implements UserService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final Random random = new Random();

    public UserRepositoryService(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user;
        if (username.contains("@")) {
            user = userRepository.findByEmail(username);
        } else {
            user = userRepository.findByUsername(username);
        }

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Username not found");
        } else {
            return new ApplicationUserDetails(user.get());
        }
    }

    @Override
    public void createUser(CreateUserRequest newUser) throws EntityExistsException, InvalidAttributeValueException {
        if (newUser.getUsername().contains("@") || !newUser.getEmail().contains("@")) {
            throw new InvalidAttributeValueException("Username or email are not correct");
        } else if (userRepository.findByUsername(newUser.getUsername()).isPresent()) {
            throw new EntityExistsException("User with a given username already exist");
        } else if (userRepository.findByEmail(newUser.getEmail()).isPresent()) {
            throw new EntityExistsException("User with a given email already exits");
        }

        Long userId = Math.abs(random.nextLong());
        while (userRepository.existsById(userId)) {
            userId++;
        }

        UserEntity userEntity = new UserEntity(userId,
                newUser.getUsername(),
                newUser.getEmail(),
                passwordEncoder.encode(newUser.getPassword()),
                null);

        userEntity.setRoles(Collections.singletonList(new UserRoleEntity(USER, userEntity)));
        userRepository.save(userEntity);
    }

    @Override
    public void deleteUser(DeleteUserRequest user) throws IllegalAccessException, EntityNotFoundException {
        Optional<UserEntity> userEntity;
        if(user.getUsernameOrEmail().contains("@")) {
            userEntity = userRepository.findByEmail(user.getUsernameOrEmail());
        } else {
            userEntity = userRepository.findByUsername(user.getUsernameOrEmail());
        }

        if(userEntity.isEmpty()) {
            throw new EntityNotFoundException();
        }
        UserEntity userToDelete = userEntity.get();

        if(passwordEncoder.matches(user.getPassword(), userToDelete.getPassword())) {
            userRepository.deleteById(userToDelete.getId());
        } else {
            throw new IllegalAccessException("Password is not correct");
        }
    }

    @Override
    public void updateUser(UpdateUserDetailsRequest updateRequest) throws AuthenticationException {
        ApplicationUserDetails user = (ApplicationUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepository.findById(updateRequest.getId()).orElseThrow();
        boolean matches = passwordEncoder.matches(updateRequest.getPassword(), userEntity.getPassword());
        if ( !matches || !user.getId().equals(updateRequest.getId())) {
            throw new AuthenticationException();
        }

        if (updateRequest.getName() != null) {
            userEntity.setUsername(updateRequest.getName());
        }
        if (updateRequest.getEmail() != null) {
            userEntity.setEmail(userEntity.getEmail());
        }
        if(updateRequest.getNewPassword() != null) {
            userEntity.setPassword(
                    passwordEncoder.encode(updateRequest.getNewPassword())
            );
        }
        userRepository.save(userEntity);
    }
}
