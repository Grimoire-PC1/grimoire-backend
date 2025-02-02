package com.grimoire.controller;

import com.grimoire.controller.documentation.UserController;
import com.grimoire.dto.user.UserCreateRequestDto;
import com.grimoire.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserControllerImpl implements UserController {
    private final UserService userService;
    @Autowired
    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> createUser(@Validated @RequestBody UserCreateRequestDto userDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userDto));
    }
}
