package com.kw.nodeimageeditorbackend.services;

import com.kw.nodeimageeditorbackend.request.AuthenticationRequest;

public interface LoginService {
    String createAuthenticationToken(AuthenticationRequest request);
}
