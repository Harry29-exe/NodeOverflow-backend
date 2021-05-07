package com.kw.nodeimageeditorbackend.user.services;

import com.kw.nodeimageeditorbackend.user.dto.UserDto;
import com.kw.nodeimageeditorbackend.user.requests.CreateUserRequest;
import com.kw.nodeimageeditorbackend.user.requests.DeleteUserRequest;
import com.kw.nodeimageeditorbackend.user.requests.UpdateUserDetailsRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void createUser(CreateUserRequest newUser);

    void deleteUser(DeleteUserRequest user);

    void updateUser(UpdateUserDetailsRequest details);

    UserDto getUser(Long id);
}
