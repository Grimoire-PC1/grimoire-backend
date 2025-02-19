package com.grimoire.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.grimoire.dto.campaign.CampaignCreateRequestDto;
import com.grimoire.dto.campaign.CampaignPostRequestDto;
import com.grimoire.dto.campaign.CampaignResponseDto;
import com.grimoire.dto.engine.EngineTypeEnum;
import com.grimoire.model.grimoire.CampaignModel;
import com.grimoire.model.grimoire.EngineModel;
import com.grimoire.model.grimoire.UserModel;
import com.grimoire.model.joinTables.EngineTypeModel;
import com.grimoire.repository.CampaignRepository;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class CampaignControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EngineRepository engineRepository;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private CampaignRepository campaignRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static MockRestServiceServer mockServer;

    private static UserModel userModel;
    private static EngineModel engineModel;
    private static CampaignModel campaignModel;

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
    void createCampaignSuccessfully() throws Exception {
        //Arrange
        CampaignCreateRequestDto requestDto = CampaignCreateRequestDto.builder()
                .title("Campanha")
                .description("descricao")
                .pictureUrl("url")
                .build();
        String requestBody = new ObjectMapper().writeValueAsString(requestDto);

        Mockito.when(userRepository.findByUsername(Mockito.any(String.class)))
                .thenReturn(Optional.of(userModel));
        Mockito.when(campaignRepository.save(Mockito.any(CampaignModel.class)))
                .thenReturn(campaignModel);
        //Act
        String responseJsonString = mockMvc.perform(post("/campaign/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        CampaignResponseDto result = objectMapper.readValue(responseJsonString, new TypeReference<>(){});

        //Assert
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(requestDto.getTitle(), result.getTitle())
        );
    }

    @Test
    @WithMockUser(username = "testuser", roles = {""})
    void updateCampaignSuccessfully() throws Exception {
        //Arrange
        CampaignPostRequestDto requestDto = CampaignPostRequestDto.builder()
                .title("")
                .description("")
                .pictureUrl("")
                .build();
        String requestBody = new ObjectMapper().writeValueAsString(requestDto);

        Mockito.when(userRepository.findByUsername(Mockito.any(String.class)))
                .thenReturn(Optional.of(userModel));
        Mockito.when(campaignRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(campaignModel));
        Mockito.when(campaignRepository.save(Mockito.any(CampaignModel.class)))
                .thenReturn(campaignModel);
        //Act
        String responseJsonString = mockMvc.perform(put("/campaign/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id_campanha", String.valueOf(1L))
                        .content(requestBody))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        CampaignResponseDto result = objectMapper.readValue(responseJsonString, new TypeReference<>(){});

        //Assert
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(campaignModel.getTitle(), result.getTitle())
        );
    }

    @Test
    @WithMockUser(username = "testuser", roles = {""})
    void deleteCampaignSuccessfully() throws Exception {
        //Arrange
        Mockito.when(userRepository.findByUsername(Mockito.any(String.class)))
                .thenReturn(Optional.of(userModel));
        Mockito.when(campaignRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(campaignModel));
        Mockito.doNothing().when(campaignRepository).delete(Mockito.any(CampaignModel.class));
        //Act
        mockMvc.perform(delete("/campaign/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id_campanha", String.valueOf(1L))
                )
                .andExpect(status().isOk())
                .andExpect(content().string("Campaign deleted successfully!"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {""})
    void getCampaignSuccessfully() throws Exception {
        //Arrange
        Mockito.when(userRepository.findByUsername(Mockito.any(String.class)))
                .thenReturn(Optional.of(userModel));
        Mockito.when(campaignRepository.findAllFiltered(null, 1L))
                .thenReturn(List.of(campaignModel));
        //Act
        String responseJsonString = mockMvc.perform(get("/campaign/get")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        List<CampaignResponseDto> result = objectMapper.readValue(responseJsonString, new TypeReference<>(){});

        //Assert
        assertAll(
                () -> assertNotNull(result)
        );
    }

}
