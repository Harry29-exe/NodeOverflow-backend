package com.kw.nodeimageeditorbackend.security.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.constraints.NotNull;
import java.util.Collection;

public class AppAuthentication implements Authentication {
    private final ApplicationUser user;
    private ApplicationUserDetails userDetails;

    public AppAuthentication(@NotNull ApplicationUser user) {
        this.user = user;
        userDetails = new ApplicationUserDetails(userDetails.username, userDetails.email);
    }

    

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities();
    }

    @Override
    public String getCredentials() {
        return user.getPassword();
    }

    @Override
    public ApplicationUserDetails getDetails() {
        return userDetails;
    }

    @Override
    public Long getPrincipal() {
        return user.getId();
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return null;
    }

    @Data
    @AllArgsConstructor
    public static class ApplicationUserDetails {
        private String username;
        private String email;
    }
}
