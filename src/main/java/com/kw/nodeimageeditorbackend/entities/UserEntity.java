package com.kw.nodeimageeditorbackend.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity implements Serializable {

    @Id
    @Column(name = "user_id")
    private long id;
    @Column
    private String username;
    @Column
    private String email;
    @Column(length = 60)
    private String password;
    @OneToMany(mappedBy = "user")
    private List<UserRoleEntity> roles;

}
