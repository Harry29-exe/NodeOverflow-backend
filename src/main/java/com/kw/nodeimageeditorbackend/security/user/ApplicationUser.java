package com.kw.nodeimageeditorbackend.security.user;

import com.kw.nodeimageeditorbackend.user.entities.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ApplicationUser implements UserDetails {
    private final List<? extends GrantedAuthority> authorities = new ArrayList<>();
    @NotNull
    private final Long id;
    @NotNull
    private final String password;
    @NotNull
    private final String username;
    @NotNull
    private final String email;

    public ApplicationUser(UserEntity userEntity) {
        this.password = userEntity.getPassword();
        this.username = userEntity.getUsername();
        this.email = userEntity.getEmail();
        this.id = userEntity.getId();
    }

    public ApplicationUser(Long id, String username, String email, String password) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.email = email;
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
