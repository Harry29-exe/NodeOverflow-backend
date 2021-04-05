package com.kw.nodeimageeditorbackend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity implements Serializable {

    @Id
    @NotNull
    @Column(name = "user_id")
    private long id;
    @NotNull
    @Column
    private String username;
    @NotNull
    @Column
    private String email;
    @NotNull
    @Column(length = 60)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<UserRoleEntity> roles;

}
