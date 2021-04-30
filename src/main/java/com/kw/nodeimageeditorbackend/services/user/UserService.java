package com.kw.nodeimageeditorbackend.services.user;

import com.kw.nodeimageeditorbackend.request.user.CreateUserRequest;
import com.kw.nodeimageeditorbackend.request.user.DeleteUserRequest;
import com.kw.nodeimageeditorbackend.request.user.UpdateUserDetailsRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void createUser(CreateUserRequest newUser);

    void deleteUser(DeleteUserRequest user);

    void updateUser(UpdateUserDetailsRequest details);
}
