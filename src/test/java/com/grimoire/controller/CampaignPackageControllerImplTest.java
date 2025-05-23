package com.grimoire.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.grimoire.dto.engine.EngineTypeEnum;
import com.grimoire.model.grimoire.*;
import com.grimoire.model.grimoire.typeTables.EngineTypeModel;
import com.grimoire.model.grimoire.typeTables.FileTypeModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class CampaignPackageControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestTemplate restTemplate;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static MockRestServiceServer mockServer;

    private static UserModel userModel;
    private static EngineModel engineModel;
    private static CampaignModel campaignModel;
    private static CampaignPackageModel cpModelParent, cpModelChild;
    private static FileTypeModel ftModel;
    private static CampaignFileModel cfModel;
    private static ItemModel itemModel;
    private static CharacterModel characterModel;

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
        cpModelParent = CampaignPackageModel.builder()
                .id(1L)
                .userModel(userModel)
                .campaign(campaignModel)
                .parentPackage(null)
                .name("pai")
                .isPublic(true)
                .build();
        cpModelChild = CampaignPackageModel.builder()
                .id(1L)
                .userModel(userModel)
                .campaign(campaignModel)
                .parentPackage(cpModelParent)
                .name("filho")
                .isPublic(true)
                .build();
        ftModel = FileTypeModel.builder()
                .id(1L)
                .description("filetype")
                .build();
        characterModel = CharacterModel.builder()
                .id(1L)
                .user(userModel)
                .campaign(campaignModel)
                .name("nome")
                .idPicture("1L")
                .build();
        itemModel = ItemModel.builder()
                .id(1L)
                .campaign(campaignModel)
                .name("item name")
                .description("item description")
                .diceQuantity(2)
                .diceType(10)
                .diceBonus(5)
                .rollDice("rola dado")
                .itemQuantity(2)
                .build();
        cfModel = CampaignFileModel.builder()
                .id(1L)
                .campaignPackage(cpModelChild)
                .fileType(ftModel)
                .name("campmodel")
                .text("texttext")
                .idPicture("2L")
                .item(itemModel)
                .character(characterModel)
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
    void createCampaignPackageSuccessfully() throws Exception {}

    @Test
    @WithMockUser(username = "testuser", roles = {""})
    void createByCharacterCampaignPackageSuccessfully() throws Exception {}

    @Test
    @WithMockUser(username = "testuser", roles = {""})
    void updateCampaignPackageSuccessfully() throws Exception {}

    @Test
    @WithMockUser(username = "testuser", roles = {""})
    void deleteCampaignPackageSuccessfully() throws Exception {}

    @Test
    @WithMockUser(username = "testuser", roles = {""})
    void getByCampaignCampaignPackageSuccessfully() throws Exception {}

    @Test
    @WithMockUser(username = "testuser", roles = {""})
    void createFileSuccessfully() throws Exception {}

    @Test
    @WithMockUser(username = "testuser", roles = {""})
    void updateFileSuccessfully() throws Exception {}

    @Test
    @WithMockUser(username = "testuser", roles = {""})
    void deleteFileSuccessfully() throws Exception {}

    @Test
    @WithMockUser(username = "testuser", roles = {""})
    void getFileByCampaignSuccessfully() throws Exception {}

}