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

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

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
    public String postUser(UserPostRequestDto request) {
        return null;
    }

    @Override
    public String deleteUser(String username) {
        return null;
    }

    @Override
    public UserResponseDto readUser(String username) {
        return null;
    }
}
