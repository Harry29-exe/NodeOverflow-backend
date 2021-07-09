package com.kw.nodeimageeditorbackend.user.services;

import com.kw.nodeimageeditorbackend.exceptions.authorization.AuthorizationException;
import com.kw.nodeimageeditorbackend.exceptions.authorization.BadCredentialsException;
import com.kw.nodeimageeditorbackend.exceptions.authorization.UserNotFoundException;
import com.kw.nodeimageeditorbackend.exceptions.general.BadRequestException;
import com.kw.nodeimageeditorbackend.exceptions.persistence.EntityNotExistException;
import com.kw.nodeimageeditorbackend.security.user.ApplicationUser;
import com.kw.nodeimageeditorbackend.user.dto.UserDto;
import com.kw.nodeimageeditorbackend.user.entities.UserEntity;
import com.kw.nodeimageeditorbackend.user.entities.UserRoleEntity;
import com.kw.nodeimageeditorbackend.user.repositories.UserRepository;
import com.kw.nodeimageeditorbackend.user.repositories.UserRoleRepository;
import com.kw.nodeimageeditorbackend.user.requests.CreateUserRequest;
import com.kw.nodeimageeditorbackend.user.requests.DeleteUserRequest;
import com.kw.nodeimageeditorbackend.user.requests.UpdateUserDetailsRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.Collections;
import java.util.Optional;
import java.util.Random;

import static com.kw.nodeimageeditorbackend.security.user.UserRole.USER;

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
        }

        return new ApplicationUser(user.get());
    }

    @Override
    public UserDto getUser(Long id) {
        return new UserDto(userRepository.findById(id)
                .orElseThrow(EntityNotExistException::new));
    }

    @Override
    public void createUser(CreateUserRequest newUser) {
        if (userRepository.findByUsername(newUser.getUsername()).isPresent()) {
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
    public void deleteUser(DeleteUserRequest user) {
        Optional<UserEntity> userEntity;
        if (user.getUsernameOrEmail().contains("@")) {
            userEntity = userRepository.findByEmail(user.getUsernameOrEmail());
        } else {
            userEntity = userRepository.findByUsername(user.getUsernameOrEmail());
        }

        if (userEntity.isEmpty()) {
            throw new UserNotFoundException("No user with such name");
        }
        UserEntity userToDelete = userEntity.get();

        if (passwordEncoder.matches(user.getPassword(), userToDelete.getPassword())) {
            userRepository.deleteById(userToDelete.getId());
        } else {
            throw new BadCredentialsException("Password is not correct");
        }
    }

    @Override
    public void updateUser(UpdateUserDetailsRequest updateRequest) {
        ApplicationUser user = (ApplicationUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepository.findById(updateRequest.getId()).orElseThrow();

        boolean matches;
        if (updateRequest.getNewPassword() != null) {
            matches = passwordEncoder.matches(updateRequest.getPassword(), userEntity.getPassword());
        } else {
            matches = true;
        }

        //TODO not handled exceptions
        if (!matches) {
            throw new BadCredentialsException();
        } else if (!user.getId().equals(updateRequest.getId())) {
            throw new AuthorizationException();
        } else if (updateRequest.getEmail() != null && !updateRequest.getEmail().contains("@")) {
            throw new BadRequestException();
        }

        if (updateRequest.getName() != null) {
            userEntity.setUsername(updateRequest.getName());
        }
        if (updateRequest.getEmail() != null) {
            userEntity.setEmail(updateRequest.getEmail());
        }
        if (updateRequest.getNewPassword() != null) {
            userEntity.setPassword(
                    passwordEncoder.encode(updateRequest.getNewPassword())
            );
        }

        userRepository.save(userEntity);
    }
}
