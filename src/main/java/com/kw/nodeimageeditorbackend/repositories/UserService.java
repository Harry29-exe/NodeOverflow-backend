package com.kw.nodeimageeditorbackend.repositories;

import com.kw.nodeimageeditorbackend.request.CreateUserRequest;
import com.kw.nodeimageeditorbackend.request.DeleteUserRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.naming.directory.InvalidAttributeValueException;
import javax.persistence.EntityExistsException;

public interface UserService extends UserDetailsService {

    void createUser(CreateUserRequest newUser) throws EntityExistsException, InvalidAttributeValueException;

    void deleteUser(DeleteUserRequest user) throws IllegalAccessException;

    void updateUser();
}
