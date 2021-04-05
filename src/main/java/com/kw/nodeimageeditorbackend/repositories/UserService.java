package com.kw.nodeimageeditorbackend.repositories;

import com.kw.nodeimageeditorbackend.entities.User;
import org.springframework.stereotype.Service;

public interface UserService {

    User getUser(long id);

}
