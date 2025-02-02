package com.grimoire.service;

import com.grimoire.dto.user.UserCreateRequestDto;
import com.grimoire.dto.user.UserPostRequestDto;
import com.grimoire.dto.user.UserResponseDto;
import com.grimoire.model.grimoire.UserModel;
import com.grimoire.repository.UserRepository;
import com.grimoire.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public String createUser(UserCreateRequestDto userDTO) {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new RuntimeException("Username already exists!");
        }
        UserModel user = UserModel.builder()
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .gmail(userDTO.getGmail())
                .name(userDTO.getName())
                .pictureUrl(userDTO.getPictureUrl())
                .build();

        userRepository.save(user);
        return "User registered successfully!";
    }

    @Override
    @Transactional
    public String postUser(String userName, UserPostRequestDto userDTO) {
        UserModel user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userName));
        user.setName(userDTO.getName().isBlank() ? user.getUsername() : userDTO.getName());
        user.setPassword(userDTO.getPassword().isBlank() ? user.getPassword() : passwordEncoder.encode(userDTO.getPassword()));
        user.setGmail(userDTO.getGmail().isBlank() ? user.getGmail() : userDTO.getGmail());
        user.setPictureUrl(userDTO.getPictureUrl().isBlank() ? user.getPictureUrl() : userDTO.getPictureUrl());

        userRepository.save(user);
        return "User updated successfully!";
    }

    @Override
    @Transactional
    public String deleteUser(String username) {
        return null;
    }

    @Override
    public UserResponseDto readUser(String username) {
        return null;
    }
}
