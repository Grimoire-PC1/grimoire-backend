package com.grimoire.service.service;

import org.springframework.security.core.Authentication;

public interface JwtService {
    public String generateToken(Authentication authentication);
}
