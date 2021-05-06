package com.kw.nodeimageeditorbackend.user.entities;

import com.kw.nodeimageeditorbackend.security.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user_roles")
public class UserRoleEntity {

    public UserRoleEntity(@NotBlank UserRole role, @NotNull UserEntity user) {
        this.role = role;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private UserRole role;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Override
    public String toString() {
        return role.name();
    }
}
