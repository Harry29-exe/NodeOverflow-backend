package com.kw.nodeimageeditorbackend.services;

import com.kw.nodeimageeditorbackend.request.CreateUserRequest;
import com.kw.nodeimageeditorbackend.request.DeleteUserRequest;
import com.kw.nodeimageeditorbackend.request.UpdateUserDetailsRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void createUser(CreateUserRequest newUser);

    void deleteUser(DeleteUserRequest user);

    void updateUser(UpdateUserDetailsRequest details);
}
