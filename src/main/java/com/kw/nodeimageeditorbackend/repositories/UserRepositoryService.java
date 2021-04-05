package com.kw.nodeimageeditorbackend.repositories;

import com.kw.nodeimageeditorbackend.entities.UserEntity;
import com.kw.nodeimageeditorbackend.entities.UserRoleEntity;
import com.kw.nodeimageeditorbackend.request.CreateUserRequest;
import com.kw.nodeimageeditorbackend.request.DeleteUserRequest;
import com.kw.nodeimageeditorbackend.security.UserPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.directory.InvalidAttributeValueException;
import javax.persistence.EntityExistsException;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

import static com.kw.nodeimageeditorbackend.security.UserRole.USER;

@Service
public class UserRepositoryService implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Random random = new Random();

    public UserRepositoryService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
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
            return new UserPrincipal(user.get());
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

        userEntity.setRoles(Arrays.asList(new UserRoleEntity(USER, userEntity)));
        userRepository.save(userEntity);
    }

    @Override
    public void deleteUser(DeleteUserRequest user) {

    }
}
