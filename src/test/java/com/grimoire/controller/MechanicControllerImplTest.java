package com.grimoire.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.grimoire.dto.engine.EngineTypeEnum;
import com.grimoire.dto.mechanic.MechanicCreateRequestDto;
import com.grimoire.dto.mechanic.MechanicPostRequestDto;
import com.grimoire.dto.mechanic.MechanicResponseDto;
import com.grimoire.model.grimoire.EngineModel;
import com.grimoire.model.grimoire.MechanicModel;
import com.grimoire.model.grimoire.UserModel;
import com.grimoire.model.grimoire.typeTables.EngineTypeModel;
import com.grimoire.repository.EngineRepository;
import com.grimoire.repository.MechanicRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class MechanicControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EngineRepository engineRepository;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private MechanicRepository mechanicRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static MockRestServiceServer mockServer;

    private static UserModel userModel;
    private static EngineModel engineModel;
    private static MechanicModel mechanicModel;

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
        mechanicModel = MechanicModel.builder()
                .id(1L)
                .engine(engineModel)
                .description("descricao")
                .name("nome")
                .actions("acoes")
                .effects("efeitos")
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
    void createMechanicSuccessfully() throws Exception {
        //Arrange
        List<String> acoes = new ArrayList<>();
        acoes.add("acao 1");
        acoes.add("acao 2");
        List<String> efeitos = new ArrayList<>();
        efeitos.add("efeito 1");
        efeitos.add("efeito 2");
        MechanicCreateRequestDto requestDto = MechanicCreateRequestDto.builder()
                .name("nome")
                .description("descricao")
                .actions(acoes)
                .effects(efeitos)
                .build();
        String requestBody = new ObjectMapper().writeValueAsString(requestDto);

        Mockito.when(userRepository.findByUsername(Mockito.any(String.class)))
                .thenReturn(Optional.of(userModel));
        Mockito.when(engineRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(engineModel));
        Mockito.when(mechanicRepository.save(Mockito.any(MechanicModel.class)))
                .thenReturn(mechanicModel);
        //Act
        String responseJsonString = mockMvc.perform(post("/mechanic/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .param("id_sistema", String.valueOf(1L)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        MechanicResponseDto result = objectMapper.readValue(responseJsonString, new TypeReference<>(){});

        //Assert
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(requestDto.getName(), result.getName()),
                () -> assertEquals(requestDto.getDescription(), result.getDescription())
        );
    }

    @Test
    @WithMockUser(username = "testuser", roles = {""})
    void updateMechanicSuccessfully() throws Exception {
        //Arrange
        List<String> acoes = new ArrayList<>();
        acoes.add("acao 1");
        acoes.add("acao 2");
        List<String> efeitos = new ArrayList<>();
        efeitos.add("efeito 1");
        efeitos.add("efeito 2");
        MechanicPostRequestDto requestDto = MechanicPostRequestDto.builder()
                .name("nome")
                .description("descricao")
                .actions(acoes)
                .effects(efeitos)
                .build();
        String requestBody = new ObjectMapper().writeValueAsString(requestDto);

        Mockito.when(userRepository.findByUsername(Mockito.any(String.class)))
                .thenReturn(Optional.of(userModel));
        Mockito.when(mechanicRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(mechanicModel));
        Mockito.when(mechanicRepository.save(Mockito.any(MechanicModel.class)))
                .thenReturn(mechanicModel);
        //Act
        String responseJsonString = mockMvc.perform(put("/mechanic/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id_mecanica", String.valueOf(1L))
                        .content(requestBody))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        MechanicResponseDto result = objectMapper.readValue(responseJsonString, new TypeReference<>(){});

        //Assert
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(mechanicModel.getName(), result.getName())
        );
    }

    @Test
    @WithMockUser(username = "testuser", roles = {""})
    void deleteMechanicSuccessfully() throws Exception {
        //Arrange
        Mockito.when(userRepository.findByUsername(Mockito.any(String.class)))
                .thenReturn(Optional.of(userModel));
        Mockito.when(mechanicRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(mechanicModel));
        Mockito.doNothing().when(mechanicRepository).delete(Mockito.any(MechanicModel.class));
        //Act
        mockMvc.perform(delete("/mechanic/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id_mecanica", String.valueOf(1L))
                )
                .andExpect(status().isOk())
                .andExpect(content().string("Mechanic deleted successfully!"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {""})
    void getMechanicSuccessfully() throws Exception {
        //Arrange
        Mockito.when(userRepository.findByUsername(Mockito.any(String.class)))
                .thenReturn(Optional.of(userModel));
        Mockito.when(mechanicRepository.findAllFiltered(null, 1L))
                .thenReturn(List.of(mechanicModel));
        //Act
        String responseJsonString = mockMvc.perform(get("/mechanic/get")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        List<MechanicResponseDto> result = objectMapper.readValue(responseJsonString, new TypeReference<>(){});

        //Assert
        assertAll(
                () -> assertNotNull(result)
        );
    }

}
