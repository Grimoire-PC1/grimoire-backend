package com.grimoire.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.grimoire.dto.characterSheetContent.CharacterSheetContentCreateRequestDto;
import com.grimoire.dto.characterSheetContent.CharacterSheetContentPostRequestDto;
import com.grimoire.dto.characterSheetContent.CharacterSheetContentResponseDto;
import com.grimoire.dto.engine.EngineTypeEnum;
import com.grimoire.model.grimoire.*;
import com.grimoire.model.grimoire.typeTables.CharacterSheetSubTabTypeModel;
import com.grimoire.model.grimoire.typeTables.EngineTypeModel;
import com.grimoire.repository.*;
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
public class CharacterSheetContentControllerImplTest {

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

    @MockitoBean
    private CharacterSheetContentRepository contentRepository;

    @MockitoBean
    private CharacterSheetSubTabRepository subTabRepository;

    private static UserModel userModel, anotherUserModel;
    private static EngineModel engineModel;
    private static CampaignModel campaignModel;
    private static CharacterModel characterModel;
    private static CharacterSheetContentModel contentModel;
    private static CharacterSheetTabModel tabModel;
    private static CharacterSheetSubTabModel subTabModel;
    private static CharacterSheetSubTabTypeModel subTabTypeModel;

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
        campaignModel = CampaignModel.builder()
                .id(1L)
                .owner(userModel)
                .engine(engineModel)
                .title("campanha")
                .build();
        characterModel = CharacterModel.builder()
                .id(1L)
                .user(userModel)
                .campaign(campaignModel)
                .name("nome")
                .idPicture("10L")
                .build();
        subTabTypeModel = CharacterSheetSubTabTypeModel.builder()
                .id(1L)
                .description("descricao")
                .build();
        tabModel = CharacterSheetTabModel.builder()
                .id(1L)
                .engine(engineModel)
                .name("nome")
                .build();
        subTabModel = CharacterSheetSubTabModel.builder()
                .id(1L)
                .characterSheetTabModel(tabModel)
                .subTabTypeModel(subTabTypeModel)
                .name("nome")
                .build();
        contentModel = CharacterSheetContentModel.builder()
                .id(1L)
                .characterModel(characterModel)
                .subTabModel(subTabModel)
                .text("texto")
                .number(42)
                .diceQuantity(2)
                .diceType(6)
                .diceBonus(1)
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
    void createCharacterSheetContentSuccessfully() throws Exception {
        //Arrange
        List<String> content = new ArrayList<>();
        content.add("content");
        CharacterSheetContentCreateRequestDto requestDto = CharacterSheetContentCreateRequestDto.builder()
                .content(content)
                .build();
        String requestBody = new ObjectMapper().writeValueAsString(requestDto);

        Mockito.when(userRepository.findByUsername(Mockito.any(String.class)))
                .thenReturn(Optional.of(userModel));
        Mockito.when(characterRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(characterModel));
        Mockito.when(subTabRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(subTabModel));
        Mockito.when(contentRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(contentModel));
        Mockito.when(contentRepository.save(Mockito.any(CharacterSheetContentModel.class)))
                .thenReturn(contentModel);
        //Act
        String responseJsonString = mockMvc.perform(post("/character-sheet-content/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id_personagem", String.valueOf(1L))
                        .param("id_sub_aba_ficha", String.valueOf(1L))
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        CharacterSheetContentResponseDto result = objectMapper.readValue(responseJsonString, new TypeReference<>(){});

        //Assert
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(requestDto.getContent().size(), result.getContent().size())
        );
    }

    @Test
    @WithMockUser(username = "testuser", roles = {""})
    void updateCharacterSheetContentSuccessfully() throws Exception {
        // Arrange:
        List<String> content = new ArrayList<>();
        content.add("content");
        CharacterSheetContentPostRequestDto requestDto = CharacterSheetContentPostRequestDto.builder()
                .content(content)
                .build();
        String requestBody = new ObjectMapper().writeValueAsString(requestDto);

        Mockito.when(userRepository.findByUsername(Mockito.any(String.class)))
                .thenReturn(Optional.of(userModel));
        Mockito.when(characterRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(characterModel));
        Mockito.when(subTabRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(subTabModel));
        Mockito.when(contentRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(contentModel));
        Mockito.when(contentRepository.save(Mockito.any(CharacterSheetContentModel.class)))
                .thenReturn(contentModel);
        //Act
        String responseJsonString = mockMvc.perform(put("/character-sheet-content/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id_conteudo_ficha", String.valueOf(1L))
                        .content(requestBody))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        CharacterSheetContentResponseDto result = objectMapper.readValue(responseJsonString, new TypeReference<>(){});

        //Assert
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(requestDto.getContent().size(), result.getContent().size())
        );
    }

    @Test
    @WithMockUser(username = "testuser", roles = {""})
    void deleteCharacterSheetContentSuccessfully() throws Exception {
        // Arrange:
        Mockito.when(userRepository.findByUsername(Mockito.any(String.class)))
                .thenReturn(Optional.of(userModel));
        Mockito.when(characterRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(characterModel));
        Mockito.when(subTabRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(subTabModel));
        Mockito.when(contentRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(contentModel));
        Mockito.doNothing().when(contentRepository).delete(Mockito.any(CharacterSheetContentModel.class));
        // Act:
        mockMvc.perform(delete("/character-sheet-content/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id_conteudo_ficha", String.valueOf(1L))
                )
                .andExpect(status().isOk())
                .andExpect(content().string("Character Sheet Content deleted successfully!"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {""})
    void getCharacterSheetContentSuccessfully() throws Exception {
        // Arrange:
        Mockito.when(userRepository.findByUsername(Mockito.any(String.class)))
                .thenReturn(Optional.of(userModel));
        Mockito.when(characterRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(characterModel));
        Mockito.when(subTabRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(subTabModel));
        Mockito.when(contentRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(contentModel));
        Mockito.when(contentRepository.findAllFiltered(Mockito.any(Long.class), Mockito.any(Long.class), Mockito.any(Long.class)))
                .thenReturn(List.of(contentModel));

        // Act:
        String responseJsonString = mockMvc.perform(get("/character-sheet-content/get")
                        .param("id_personagem", String.valueOf(1L))
                        .param("id_aba_ficha", String.valueOf(1L))
                        .param("id_sub_aba_ficha", String.valueOf(1L))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        List<CharacterSheetContentResponseDto> result = objectMapper.readValue(responseJsonString, new TypeReference<>() {});

        // Assert:
        assertAll(
                () -> assertNotNull(result)
        );
    }

}
