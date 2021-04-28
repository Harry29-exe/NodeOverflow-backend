package com.kw.nodeimageeditorbackend.services.user;

import com.kw.nodeimageeditorbackend.request.AuthenticationRequest;

public interface LoginService {
    String createAuthenticationToken(AuthenticationRequest request);
}
