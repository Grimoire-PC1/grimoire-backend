package com.grimoire.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.grimoire.dto.engine.EngineTypeEnum;
import com.grimoire.dto.participant.ParticipantResponseDto;
import com.grimoire.model.grimoire.CampaignModel;
import com.grimoire.model.grimoire.EngineModel;
import com.grimoire.model.grimoire.ParticipantModel;
import com.grimoire.model.grimoire.UserModel;
import com.grimoire.model.grimoire.embeddedId.ParticipantModelId;
import com.grimoire.model.grimoire.typeTables.EngineTypeModel;
import com.grimoire.repository.CampaignRepository;
import com.grimoire.repository.EngineRepository;
import com.grimoire.repository.ParticipantRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class ParticipantControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EngineRepository engineRepository;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private CampaignRepository campaignRepository;

    @MockitoBean
    private ParticipantRepository participantRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static MockRestServiceServer mockServer;

    private static UserModel userModel, anotherUserModel;
    private static EngineModel engineModel;
    private static CampaignModel campaignModel;
    private static ParticipantModelId pModelId;
    private static ParticipantModel pModel;

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
        pModelId = ParticipantModelId.builder()
                .userModel(userModel)
                .campaignModel(campaignModel)
                .build();
        pModel = ParticipantModel.builder()
                .modelId(pModelId)
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
    void createParticipantSuccessfully() throws Exception {
        // Arrange:
        Mockito.when(userRepository.findByUsername(Mockito.any(String.class)))
                .thenReturn(Optional.of(anotherUserModel));
        Mockito.when(campaignRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(campaignModel));
        Mockito.when(participantRepository.save(Mockito.any(ParticipantModel.class)))
                .thenReturn(pModel);

        // Act:
        String responseJsonString = mockMvc.perform(post("/participant/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id_campanha", String.valueOf(1L)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        ParticipantResponseDto result = objectMapper.readValue(responseJsonString, new TypeReference<>(){});

        // Assert:
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(pModel.getModelId().getCampaignModel().getId(), result.getIdCampaign())
        );
    }

    @Test
    @WithMockUser(username = "testuser", roles = {""})
    void deleteParticipantSuccessfully() throws Exception {
        // Arrange:
        Mockito.when(userRepository.findByUsername(Mockito.any(String.class)))
                .thenReturn(Optional.of(userModel));
        Mockito.when(campaignRepository.findById(Mockito.any(Long.class)))
            .thenReturn(Optional.of(campaignModel));
        Mockito.when(participantRepository.findById(Mockito.any(ParticipantModelId.class)))
                .thenReturn(Optional.of(pModel));
        Mockito.doNothing().when(participantRepository).removeParticipantData(Mockito.any(Long.class), Mockito.any(Long.class));
        // Act:
        mockMvc.perform(delete("/participant/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id_campanha", String.valueOf(1L))
                )
                .andExpect(status().isOk())
                .andExpect(content().string("Participant deleted successfully!"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {""})
    void getParticipantSuccessfully() throws Exception {
        // Arrange:
        Mockito.when(userRepository.findByUsername(Mockito.any(String.class)))
                .thenReturn(Optional.of(userModel));
        Mockito.when(campaignRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(campaignModel));
        Mockito.when(participantRepository.findAllByCampaign(1L))
                .thenReturn(List.of(pModel));
        // Act:
        String responseJsonString = mockMvc.perform(get("/participant/get")
                        .param("id_campanha", String.valueOf(1L))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        List<ParticipantResponseDto> result = objectMapper.readValue(responseJsonString, new TypeReference<>(){});

        // Assert:
        assertAll(
                () -> assertNotNull(result)
        );
    }

}
