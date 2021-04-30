package com.kw.nodeimageeditorbackend.services.user;

import com.kw.nodeimageeditorbackend.request.user.AuthenticationRequest;

public interface LoginService {
    String createAuthenticationToken(AuthenticationRequest request);
}
