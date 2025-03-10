package com.grimoire.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.grimoire.dto.character.CharacterCreateRequestDto;
import com.grimoire.dto.character.CharacterPostRequestDto;
import com.grimoire.dto.character.CharacterResponseDto;
import com.grimoire.dto.engine.EngineTypeEnum;
import com.grimoire.model.grimoire.*;
import com.grimoire.model.grimoire.joinTables.EngineTypeModel;
import com.grimoire.repository.CampaignRepository;
import com.grimoire.repository.CharacterRepository;
import com.grimoire.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CharacterControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestTemplate restTemplate;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static MockRestServiceServer mockServer;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private CampaignRepository campaignRepository;

    @MockitoBean
    private CharacterRepository characterRepository;

    private static UserModel userModel, anotherUserModel;
    private static EngineModel engineModel;
    private static CampaignModel campaignModel;
    private static CharacterModel characterModel;

    @BeforeAll
    static void initResources() {
        userModel = UserModel.builder()
                .id(1L)
                .build();
        anotherUserModel = UserModel.builder()
                .id(2L)
                .build();
        engineModel = EngineModel.builder()
                .id(1L)
                .owner(userModel)
                .engineType(new EngineTypeModel(EngineTypeEnum.PUBLICO))
                .build();
        campaignModel = CampaignModel.builder()
                .id(1L)
                .owner(userModel)
                .engine(engineModel)
                .title("Campanha")
                .build();
        characterModel = CharacterModel.builder()
                .id(1L)
                .user(userModel)
                .campaign(campaignModel)
                .name("nome")
                .idPicture(10L)
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
    void createCharacterSuccessfully() throws Exception {
        // Arrange:
        CharacterCreateRequestDto requestDto = CharacterCreateRequestDto.builder()
                .name("nome")
                .idPicture(10L)
                .build();
        String requestBody = new ObjectMapper().writeValueAsString(requestDto);

        Mockito.when(userRepository.findByUsername(Mockito.any(String.class)))
                .thenReturn(Optional.of(userModel));
        Mockito.when(campaignRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(campaignModel));
        Mockito.when(characterRepository.save(Mockito.any(CharacterModel.class)))
                .thenReturn(characterModel);

        // Act:
        String responseJsonString = mockMvc.perform(post("/character/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id_campanha", String.valueOf(1L))
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        CharacterResponseDto result = objectMapper.readValue(responseJsonString, new TypeReference<>() {});

        // Assert:
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(requestDto.getName(), result.getName()),
                () -> assertEquals(requestDto.getIdPicture(), result.getIdPicture())
        );
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void updateCharacterSuccessfully() throws Exception {
        // Arrange:
        CharacterPostRequestDto requestDto = CharacterPostRequestDto.builder()
                .name("nome")
                .idPicture(10L)
                .build();
        String requestBody = new ObjectMapper().writeValueAsString(requestDto);

        Mockito.when(userRepository.findByUsername(Mockito.any(String.class)))
                .thenReturn(Optional.of(userModel));
        Mockito.when(characterRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(characterModel));
        Mockito.when(characterRepository.save(Mockito.any(CharacterModel.class)))
                .thenReturn(characterModel);

        // Act
        String responseJsonString = mockMvc.perform(put("/character/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id_personagem", String.valueOf(1L))
                        .content(requestBody))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        CharacterResponseDto result = objectMapper.readValue(responseJsonString, new TypeReference<>(){});

        // Assert:
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(characterModel.getName(), result.getName()),
                () -> assertEquals(characterModel.getIdPicture(), result.getIdPicture())
        );
    }

    @Test
    @WithMockUser(username = "testuser", roles = {""})
    void deleteCharacterSuccessfully() throws Exception {
        // Arrange:
        Mockito.when(userRepository.findByUsername(Mockito.any(String.class)))
                .thenReturn(Optional.of(userModel));
        Mockito.when(campaignRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(campaignModel));
        Mockito.when(characterRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(characterModel));
        Mockito.doNothing().when(characterRepository).delete(Mockito.any(CharacterModel.class));
        // Act:
        mockMvc.perform(delete("/character/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id_personagem", String.valueOf(1L))
                )
                .andExpect(status().isOk())
                .andExpect(content().string("Character deleted successfully!"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {""})
    void getCharacterByUserSuccessfully() throws Exception {
        // Arrange:
        Mockito.when(userRepository.findByUsername(Mockito.any(String.class)))
                .thenReturn(Optional.of(userModel));
        Mockito.when(campaignRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(campaignModel));
        Mockito.when(characterRepository.findAllFiltered(1L, null)) // Correção aqui
                .thenReturn(List.of(characterModel));

        // Act:
        String responseJsonString = mockMvc.perform(get("/character/get/user")
                        .param("id_usuario", String.valueOf(1L)) // Ajustar para corresponder ao esperado no controller
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        List<CharacterResponseDto> result = objectMapper.readValue(responseJsonString, new TypeReference<>() {});

        // Assert:
        assertAll(
                () -> assertNotNull(result)
        );
    }

    @Test
    @WithMockUser(username = "testuser", roles = {""})
    void getCharacterByCampaignSuccessfully() throws Exception {
        // Arrange:
        Mockito.when(userRepository.findByUsername(Mockito.any(String.class)))
                .thenReturn(Optional.of(userModel));
        Mockito.when(campaignRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(campaignModel));
        Mockito.when(characterRepository.findAllFiltered(null, 1L)) // Correção aqui
                .thenReturn(List.of(characterModel));

        // Act:
        String responseJsonString = mockMvc.perform(get("/character/get/campaign")
                        .param("id_campanha", String.valueOf(1L))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        List<CharacterResponseDto> result = objectMapper.readValue(responseJsonString, new TypeReference<>() {});

        // Assert:
        assertAll(
                () -> assertNotNull(result)
        );
    }
}