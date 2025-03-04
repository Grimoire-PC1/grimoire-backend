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
                .email(userDTO.getEmail())
                .name(userDTO.getName())
                .pictureID(userDTO.getPictureID())
                .build();

        userRepository.save(user);
        return "User registered successfully!";
    }

    @Override
    @Transactional
    public String postUser(String userName, UserPostRequestDto userDTO) {
        UserModel user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userName));
        user.setName(userDTO.getName().isBlank() ? user.getName() : userDTO.getName());
        user.setPassword(userDTO.getPassword().isBlank() ? user.getPassword() : passwordEncoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail().isBlank() ? user.getEmail() : userDTO.getEmail());
        user.setPictureID(userDTO.getPictureID().isBlank() ? user.getPictureID() : userDTO.getPictureID());

        userRepository.save(user);
        return "User updated successfully!";
    }

    @Override
    @Transactional
    public String deleteUser(String userName) {
        UserModel user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userName));

        userRepository.delete(user);
        return "User deleted successfully!";
    }

    @Override
    public UserResponseDto getUser(String userName) {
        UserModel user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userName));

        return user.toDto();
    }
}
