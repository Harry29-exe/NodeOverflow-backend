package com.kw.nodeimageeditorbackend.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user_roles")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String role;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
