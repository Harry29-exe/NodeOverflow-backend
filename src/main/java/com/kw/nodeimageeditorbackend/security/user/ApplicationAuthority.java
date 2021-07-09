package com.kw.nodeimageeditorbackend.security.user;

import org.springframework.security.core.GrantedAuthority;

public class ApplicationAuthority implements GrantedAuthority {

    private String authority;

//    public ApplicationAuthority(UserRole userRole) {
//        this.authority = userRole.permissions;
//    }

    @Override
    public String getAuthority() {
        return null;
    }

}
