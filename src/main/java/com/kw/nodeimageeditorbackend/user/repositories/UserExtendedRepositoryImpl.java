package com.kw.nodeimageeditorbackend.user.repositories;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class UserExtendedRepositoryImpl implements UserExtendedRepository {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void updateEmail(long userId, String newEmail) {
        Query query = entityManager.createQuery(
                "UPDATE UserEntity SET email = :email WHERE id = :userId"
        );
        query.setParameter("email", newEmail);
        query.setParameter("userId", userId);

        query.executeUpdate();
    }

    @Override
    public void updateUsername(long userId, String newUsername) {
        Query query = entityManager.createQuery(
                "UPDATE UserEntity SET username = :username WHERE id = :userId"
        );
        query.setParameter("username", newUsername);
        query.setParameter("userId", userId);

        query.executeUpdate();
    }

    @Override
    public void updatePassword(long userId, String encodedPassword) {
        Query query = entityManager.createQuery(
                "UPDATE UserEntity SET password = :password WHERE id = :userId"
        );
        query.setParameter("password", encodedPassword);
        query.setParameter("userId", userId);

        query.executeUpdate();
    }
}
