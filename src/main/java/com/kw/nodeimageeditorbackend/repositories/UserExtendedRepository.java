package com.kw.nodeimageeditorbackend.repositories;


public interface UserExtendedRepository {
    void updateEmail(long userId, String newEmail);

    void updateUsername(long userId, String newUsername);

    void updatePassword(long userId, String encodedPassword);
}
