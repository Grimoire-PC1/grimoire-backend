package com.grimoire.service.interfaces;

import org.springframework.security.core.Authentication;

public interface JwtService {
    String generateToken(Authentication authentication);
}
