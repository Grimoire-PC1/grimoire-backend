package com.grimoire.controller;

import com.grimoire.controller.documentation.AuthController;
import com.grimoire.service.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
public class AuthControllerImpl implements AuthController {
    private final AuthService authService;

    @Autowired
    public AuthControllerImpl(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/authenticate")
    public String authenticate(Authentication authentication) {
        return authService.authenticate(authentication);
    }

}
