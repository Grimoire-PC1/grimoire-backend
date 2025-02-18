package com.grimoire.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.grimoire.dto.user.UserCreateRequestDto;
import com.grimoire.dto.user.UserPostRequestDto;
import com.grimoire.dto.user.UserResponseDto;
import com.grimoire.model.grimoire.UserModel;
import com.grimoire.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;


import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@Import(UserControllerImpl.class)
class UserControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static MockRestServiceServer mockServer;

    @BeforeEach
    void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @AfterEach
    void tearDown() {
        mockServer.reset();
    }

    @Test
    void createUserSuccessfully() throws Exception {
        UserCreateRequestDto requestDto = UserCreateRequestDto.builder()
                .username("username")
                .password("password")
                .name("name")
                .email("email")
                .build();
        String requestBody = new ObjectMapper().writeValueAsString(requestDto);

        Mockito.when(userRepository.existsByUsername(Mockito.any(String.class)))
                .thenReturn(false);
        Mockito.when(userRepository.save(Mockito.any(UserModel.class)))
                .thenReturn(new UserModel());

        String responseJsonString = mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().string("User registered successfully!"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    @WithMockUser(username = "testuser", roles = {""})
    void updateUserSuccessfully() throws Exception {
        UserPostRequestDto requestDto = UserPostRequestDto.builder()
                .password("password")
                .name("name")
                .email("email")
                .pictureUrl("")
                .build();
        String requestBody = new ObjectMapper().writeValueAsString(requestDto);

        Mockito.when(userRepository.findByUsername(Mockito.any(String.class)))
                .thenReturn(Optional.of(new UserModel()));
        Mockito.when(userRepository.save(Mockito.any(UserModel.class)))
                .thenReturn(new UserModel());

        mockMvc.perform(put("/user/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )
                .andExpect(status().isOk())
                .andExpect(content().string("User updated successfully!"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {""})
    void deleteUserSuccessfully() throws Exception {
        Mockito.when(userRepository.findByUsername(Mockito.any(String.class)))
                .thenReturn(Optional.of(new UserModel()));
        Mockito.doNothing().when(userRepository).delete(Mockito.any(UserModel.class));

        mockMvc.perform(delete("/user/delete")
                 )
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted successfully!"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {""})
    void getUserSuccessfully() throws Exception {
        UserModel user = UserModel.builder()
                .username("testuser")
                .email("email")
                .name("name")
                .pictureUrl("url")
                .build();
        UserResponseDto responseDto = user.toDto();
        String responseBody = new ObjectMapper().writeValueAsString(responseDto);

        Mockito.when(userRepository.findByUsername(Mockito.any(String.class)))
                .thenReturn(Optional.of(user));

        mockMvc.perform(get("/user/get")
                 )
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody))
                .andDo(print());
    }
}
