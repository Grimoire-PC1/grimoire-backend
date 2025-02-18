package com.grimoire.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.grimoire.dto.engine.EngineCreateRequestDto;
import com.grimoire.dto.engine.EngineEditRequestDto;
import com.grimoire.dto.engine.EngineResponseDto;
import com.grimoire.dto.engine.EngineTypeEnum;
import com.grimoire.model.grimoire.EngineModel;
import com.grimoire.model.grimoire.UserModel;
import com.grimoire.model.grimoire.joinTables.EngineTypeModel;
import com.grimoire.repository.EngineRepository;
import com.grimoire.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class EngineControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EngineRepository engineRepository;

    @MockitoBean
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static MockRestServiceServer mockServer;

    private static UserModel userModel;
    private static EngineModel engineModel;

    @BeforeAll
    static void initResources() {
        userModel = UserModel.builder()
                .id(1L)
                .build();
        engineModel = EngineModel.builder()
                .id(1L)
                .owner(userModel)
                .engineType(new EngineTypeModel(EngineTypeEnum.PUBLICO))
                .build();
    }

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
    @WithMockUser(username = "testuser", roles = {""})
    void createEngineSuccessfully() throws Exception {
        //Arrange
        EngineCreateRequestDto requestDto = EngineCreateRequestDto.builder()
                .name("sistema")
                .description("descricao")
                .pictureUrl("url")
                .build();
        String requestBody = new ObjectMapper().writeValueAsString(requestDto);

        Mockito.when(userRepository.findByUsername(Mockito.any(String.class)))
                .thenReturn(Optional.of(userModel));
        Mockito.when(engineRepository.save(Mockito.any(EngineModel.class)))
                .thenReturn(engineModel);
        //Act
        String responseJsonString = mockMvc.perform(post("/engine/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("tipo_sistema", EngineTypeEnum.PUBLICO.name())
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        EngineResponseDto result = objectMapper.readValue(responseJsonString, new TypeReference<>(){});

        //Assert
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(requestDto.getName(), result.getName()),
                () -> assertEquals(EngineTypeEnum.PUBLICO.name(), result.getTypeSys())
        );
    }

    @Test
    @WithMockUser(username = "testuser", roles = {""})
    void updateEngineSuccessfully() throws Exception {
        //Arrange
        EngineEditRequestDto requestDto = EngineEditRequestDto.builder()
                .name("sistema")
                .description("descricao")
                .pictureUrl("url")
                .build();
        String requestBody = new ObjectMapper().writeValueAsString(requestDto);

        Mockito.when(userRepository.findByUsername(Mockito.any(String.class)))
                .thenReturn(Optional.of(userModel));
        Mockito.when(engineRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(engineModel));
        Mockito.when(engineRepository.save(Mockito.any(EngineModel.class)))
                .thenReturn(engineModel);
        //Act
        String responseJsonString = mockMvc.perform(put("/engine/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id_sistema", String.valueOf(1L))
                        .param("tipo_sistema", EngineTypeEnum.PUBLICO.name())
                        .content(requestBody))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        EngineResponseDto result = objectMapper.readValue(responseJsonString, new TypeReference<>(){});

        //Assert
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(requestDto.getName(), result.getName()),
                () -> assertEquals(EngineTypeEnum.PUBLICO.name(), result.getTypeSys())
        );
    }

    @Test
    @WithMockUser(username = "testuser", roles = {""})
    void deleteEngineSuccessfully() throws Exception {
        //Arrange
        Mockito.when(userRepository.findByUsername(Mockito.any(String.class)))
                .thenReturn(Optional.of(userModel));
        Mockito.when(engineRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(engineModel));
        Mockito.doNothing().when(engineRepository).delete(Mockito.any(EngineModel.class));
        //Act
        mockMvc.perform(delete("/engine/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id_sistema", String.valueOf(1L))
                )
                .andExpect(status().isOk())
                .andExpect(content().string("Engine deleted successfully!"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {""})
    void getEngineSuccessfully() throws Exception {
        //Arrange
        Mockito.when(userRepository.findByUsername(Mockito.any(String.class)))
                .thenReturn(Optional.of(userModel));
        Mockito.when(engineRepository.findAllFiltered(null, 1L))
                .thenReturn(List.of(engineModel));
        //Act
        String responseJsonString = mockMvc.perform(get("/engine/get")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        List<EngineResponseDto> result = objectMapper.readValue(responseJsonString, new TypeReference<>(){});

        //Assert
        assertAll(
                () -> assertNotNull(result)
        );
    }

    @Test
    @WithMockUser(username = "testuser", roles = {""})
    void getPublicEngineSuccessfully() throws Exception {
        //Arrange
        Mockito.when(engineRepository.findAllByEngineType_Id(EngineTypeEnum.PUBLICO.getId()))
                .thenReturn(List.of(engineModel));
        //Act
        String responseJsonString = mockMvc.perform(get("/engine/get-public")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        List<EngineResponseDto> result = objectMapper.readValue(responseJsonString, new TypeReference<>(){});

        //Assert
        assertAll(
                () -> assertNotNull(result)
        );
    }
}
