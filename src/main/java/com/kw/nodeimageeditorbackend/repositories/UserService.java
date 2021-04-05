package com.kw.nodeimageeditorbackend.repositories;

import com.kw.nodeimageeditorbackend.request.CreateUserRequest;
import com.kw.nodeimageeditorbackend.request.DeleteUserRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void createUser(CreateUserRequest newUser);

    void deleteUser(DeleteUserRequest user);
}
