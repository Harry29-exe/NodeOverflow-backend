package com.kw.nodeimageeditorbackend.user.repositories;


public interface UserExtendedRepository {
    void updateEmail(long userId, String newEmail);

    void updateUsername(long userId, String newUsername);

    void updatePassword(long userId, String encodedPassword);
}
