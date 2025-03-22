package com.grimoire.service.interfaces;

import org.springframework.security.core.Authentication;

public interface AuthService {
    String authenticate(Authentication authentication);
}
