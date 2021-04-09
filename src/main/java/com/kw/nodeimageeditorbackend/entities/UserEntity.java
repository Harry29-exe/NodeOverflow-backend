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
    @Column(name = "user_id")
    private Long id;

    @Column
    private String username;

    @Column
    private String email;

    @Column(length = 60)
    private String password;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", targetEntity = UserRoleEntity.class, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserRoleEntity> roles;

//    @PreRemove
//    public void preRemove() {
//        roles.forEach(r -> r.setUser(null));
//    }
}
