package com.kw.nodeimageeditorbackend.repositories.user;

import com.kw.nodeimageeditorbackend.entities.user.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {
}