package com.grimoire.service.service;

import org.springframework.security.core.Authentication;

public interface AuthService {
    String authenticate(Authentication authentication);
}
