package com.kw.nodeimageeditorbackend.security;

import com.kw.nodeimageeditorbackend.entities.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {
    private List<? extends GrantedAuthority> authorities = new ArrayList<>();
    private Long id;
    private String password;
    private String username;
    private String email;

    public UserPrincipal(UserEntity userEntity) {
        this.password = userEntity.getPassword();
        this.username = userEntity.getUsername();
        this.email = userEntity.getEmail();
        this.id = userEntity.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
