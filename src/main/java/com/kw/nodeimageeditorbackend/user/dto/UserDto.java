package com.kw.nodeimageeditorbackend.user.dto;

import com.kw.nodeimageeditorbackend.user.entities.UserEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
