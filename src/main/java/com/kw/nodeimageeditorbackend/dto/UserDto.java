package com.kw.nodeimageeditorbackend.dto;

import com.kw.nodeimageeditorbackend.entities.user.UserEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

//TODO ask about microservices
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserDto {
    @EqualsAndHashCode.Include
    private Long id;
    private String username;
    private String email;

    public UserDto(UserEntity entity) {
        id = entity.getId();
        username = entity.getUsername();
        email = entity.getEmail();
    }
}
