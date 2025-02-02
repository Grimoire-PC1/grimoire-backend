package com.grimoire.service.service;

import com.grimoire.dto.user.UserCreateRequestDto;
import com.grimoire.dto.user.UserPostRequestDto;
import com.grimoire.dto.user.UserResponseDto;

public interface UserService {
    String createUser(UserCreateRequestDto userDTO);
    String postUser(String userName, UserPostRequestDto request);
    String deleteUser(String username);
    UserResponseDto readUser(String username);

}
