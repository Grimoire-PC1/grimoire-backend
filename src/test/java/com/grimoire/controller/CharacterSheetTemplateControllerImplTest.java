package com.grimoire.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.grimoire.dto.engine.EngineTypeEnum;
import com.grimoire.model.grimoire.*;
import com.grimoire.model.grimoire.typeTables.CharacterSheetSubTabTypeModel;
import com.grimoire.model.grimoire.typeTables.EngineTypeModel;
import com.grimoire.repository.CampaignRepository;
import com.grimoire.repository.CharacterRepository;
import com.grimoire.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
@AutoConfigureMockMvc
public class CharacterSheetTemplateControllerImplTest {

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
    private static CharacterSheetTabModel cstm;
    private static CharacterSheetSubTabModel csstm;
    private static CharacterSheetSubTabTypeModel cssttm;

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
                .title("Campanha")
                .build();
        characterModel = CharacterModel.builder()
                .id(1L)
                .user(userModel)
                .campaign(campaignModel)
                .name("nome")
                .idPicture("10L")
                .build();
        cssttm = CharacterSheetSubTabTypeModel.builder()
                .id(1L)
                .description("descricao")
                .build();
        cstm = CharacterSheetTabModel.builder()
                .id(1L)
                .engine(engineModel)
                .name("cstm")
                .build();
        csstm = CharacterSheetSubTabModel.builder()
                .id(1L)
                .characterSheetTabModel(cstm)
                .subTabTypeModel(cssttm)
                .name("csstm")
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
    void createCharacterSheetTemplateSuccessfully() throws Exception {}

    @Test
    @WithMockUser(username = "testuser", roles = {""})
    void updateCharacterSheetTemplateSuccessfully() throws Exception {}

    @Test
    @WithMockUser(username = "testuser", roles = {""})
    void deleteCharacterSheetTemplateSuccessfully() throws Exception {}

    @Test
    @WithMockUser(username = "testuser", roles = {""})
    void getCharacterSheetTemplateSuccessfully() throws Exception {}

    @Test
    @WithMockUser(username = "testuser", roles = {""})
    void getByCampaignCharacterSheetTemplateSuccessfully() throws Exception {}

    @Test
    @WithMockUser(username = "testuser", roles = {""})
    void createCharacterSheetSubTemplateSuccessfully() throws Exception {}

    @Test
    @WithMockUser(username = "testuser", roles = {""})
    void updateCharacterSheetSubTemplateSuccessfully() throws Exception {}

    @Test
    @WithMockUser(username = "testuser", roles = {""})
    void deleteCharacterSheetSubTemplateSuccessfully() throws Exception {}

    @Test
    @WithMockUser(username = "testuser", roles = {""})
    void getCharacterSheetSubTemplateSuccessfully() throws Exception {}

    @Test
    @WithMockUser(username = "testuser", roles = {""})
    void getByCampaignCharacterSheetSubTemplateSuccessfully() throws Exception {}

}
