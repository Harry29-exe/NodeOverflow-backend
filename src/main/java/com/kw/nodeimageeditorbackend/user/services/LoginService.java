package com.kw.nodeimageeditorbackend.user.services;

import com.kw.nodeimageeditorbackend.user.requests.AuthenticationRequest;

public interface LoginService {
    String createAuthenticationToken(AuthenticationRequest request);

    String createRefreshToken(AuthenticationRequest request);

    String refreshToken(String refreshToken);
}
