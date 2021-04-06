package com.kw.nodeimageeditorbackend.repositories;

import com.kw.nodeimageeditorbackend.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Modifying
    @Query("DELETE FROM UserEntity WHERE id = ?1")
    void deleteById(Long userId);

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);


}
