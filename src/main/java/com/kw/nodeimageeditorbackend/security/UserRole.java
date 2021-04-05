package com.kw.nodeimageeditorbackend.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;
import java.util.stream.Stream;

public enum UserRole {
    ADMIN(new HashSet<>(Arrays.asList(UserPermission.CREATE_USER, UserPermission.DELETE_USER))),
    USER(new HashSet<>()),
    MOD((new HashSet<>(Arrays.asList(UserPermission.CREATE_USER, UserPermission.DELETE_USER))));

    public final Set<UserPermission> permissions;

    UserRole(Set<UserPermission> permissions) {
        this.permissions = permissions;
    }

    public List<GrantedAuthority> getGrantedAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        this.permissions.forEach(p -> authorities.add(new SimpleGrantedAuthority(p.name())));
        return authorities;
    }
}
