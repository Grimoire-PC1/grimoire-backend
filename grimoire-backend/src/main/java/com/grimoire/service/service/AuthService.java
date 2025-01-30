package com.grimoire.service.service;

import org.springframework.security.core.Authentication;

public interface AuthService {
    public String authenticate(Authentication authentication);
}
