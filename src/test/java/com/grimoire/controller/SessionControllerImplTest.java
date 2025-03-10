package com.grimoire.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.grimoire.dto.engine.EngineTypeEnum;
import com.grimoire.dto.session.SessionCreateRequestDto;
import com.grimoire.dto.session.SessionPostRequestDto;
import com.grimoire.dto.session.SessionResponseDto;
import com.grimoire.model.grimoire.CampaignModel;
import com.grimoire.model.grimoire.EngineModel;
import com.grimoire.model.grimoire.SessionModel;
import com.grimoire.model.grimoire.UserModel;
import com.grimoire.model.grimoire.joinTables.EngineTypeModel;
import com.grimoire.model.grimoire.joinTables.SessionTypeModel;
import com.grimoire.repository.CampaignRepository;
import com.grimoire.repository.EngineRepository;
import com.grimoire.repository.SessionRepository;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
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
public class SessionControllerImplTest {

    @MockitoBean
    private EngineRepository engineRepository;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private CampaignRepository campaignRepository;

    @MockitoBean
    private SessionRepository sessionRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestTemplate restTemplate;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static MockRestServiceServer mockServer;

    private static UserModel userModel;
    private static EngineModel engineModel;
    private static CampaignModel campaignModel;
    private static SessionTypeModel sessionTM;
    private static SessionModel sessionModel;

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
        sessionTM = SessionTypeModel.builder()
                .id(1L)
                .description("Descricao")
                .build();
        sessionModel = SessionModel.builder()
                .id(1L)
                .campaign(campaignModel)
                .title("Sessao")
                .date("data")
                .description("Descricao")
                .sessionType(sessionTM)
                .fixed(true)
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
    void createSessionSuccessfully() throws Exception {
        // Arrange:
        SessionCreateRequestDto requestDto = SessionCreateRequestDto.builder()
                .title("Sessao")
                .date("data")
                .description("descricao")
                .fixed(true)
                .build();
        String requestBody = new ObjectMapper().writeValueAsString(requestDto);

        Mockito.when(userRepository.findByUsername(Mockito.any(String.class)))
                .thenReturn(Optional.of(userModel));
        Mockito.when(campaignRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(campaignModel));
        Mockito.when(sessionRepository.save(Mockito.any(SessionModel.class)))
                .thenReturn(sessionModel);

        // Act:
        String responseJsonString = mockMvc.perform(post("/session/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id_campanha", String.valueOf(1L))
                        .param("tipo_sessao", "FUTURA")
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        SessionResponseDto result = objectMapper.readValue(responseJsonString, new TypeReference<>(){});

        // Assert:
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(requestDto.getTitle(), result.getTitle())
        );

    }

    @Test
    @WithMockUser(username = "testuser", roles = {""})
    void updateSessionSuccessfully() throws Exception {
        // Arrange:
        SessionPostRequestDto requestDto = SessionPostRequestDto.builder()
                .title("Sessao")
                .date("data")
                .description("descricao")
                .fixed(true)
                .build();
        String requestBody = new ObjectMapper().writeValueAsString(requestDto);

        Mockito.when(userRepository.findByUsername(Mockito.any(String.class)))
                .thenReturn(Optional.of(userModel));
        Mockito.when(campaignRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(campaignModel));
        Mockito.when(sessionRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(sessionModel));
        Mockito.when(sessionRepository.save(Mockito.any(SessionModel.class)))
                .thenReturn(sessionModel);

        // Act:
        String responseJsonString = mockMvc.perform(MockMvcRequestBuilders.put("/session/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id_sessao", String.valueOf(1L))
                        .param("novo_tipo_sessao", "FUTURA")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        SessionResponseDto result = objectMapper.readValue(responseJsonString, new TypeReference<>(){});

        //Assert
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(sessionModel.getTitle(), result.getTitle()),
                () -> assertEquals(sessionModel.getDescription(), result.getDescription()),
                () -> assertEquals(sessionModel.getDate(), result.getDate())
        );

    }

    @Test
    @WithMockUser(username = "testuser", roles = {""})
    void deleteSessionSuccessfully() throws Exception {
        // Arrange:
        Mockito.when(userRepository.findByUsername(Mockito.any(String.class)))
                .thenReturn(Optional.of(userModel));
        Mockito.when(campaignRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(campaignModel));
        Mockito.when(sessionRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(sessionModel));
        Mockito.doNothing().when(sessionRepository).delete(Mockito.any(SessionModel.class));

        // Act:
        mockMvc.perform(delete("/session/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id_sessao", String.valueOf(1L)))
                .andExpect(status().isOk())
                .andExpect(content().string("Session deleted successfully!"))
                .andDo(print());

    }

    @Test
    @WithMockUser(username = "testuser", roles = {""})
    void getSessionByCampaignSuccessfully() throws Exception {
        // Arrange:
        Mockito.when(userRepository.findByUsername(Mockito.any(String.class)))
                .thenReturn(Optional.of(userModel));
        Mockito.when(campaignRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(campaignModel));
        Mockito.when(sessionRepository.findAllByCampaign(1L))
                .thenReturn(List.of(sessionModel));

        // Act:
        String responseJsonString = mockMvc.perform(get("/session/get/campaign")
                        .param("id_campanha", String.valueOf(1L))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        List<SessionResponseDto> result = objectMapper.readValue(responseJsonString, new TypeReference<>() {});

        // Assert:
        assertAll(
                () -> assertNotNull(result)
        );
    }

}
