package com.grimoire.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.grimoire.dto.engine.EngineTypeEnum;
import com.grimoire.dto.engineRule.RuleCreateRequestDto;
import com.grimoire.dto.engineRule.RulePostRequestDto;
import com.grimoire.dto.engineRule.RuleResponseDto;
import com.grimoire.model.grimoire.EngineModel;
import com.grimoire.model.grimoire.EngineRuleModel;
import com.grimoire.model.grimoire.UserModel;
import com.grimoire.model.grimoire.typeTables.EngineTypeModel;
import com.grimoire.repository.EngineRepository;
import com.grimoire.repository.EngineRuleRepository;
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
public class EngineRuleControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EngineRepository engineRepository;

    @MockitoBean
    private EngineRuleRepository ruleRepository;

    @MockitoBean
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static MockRestServiceServer mockServer;

    private static UserModel userModel;
    private static EngineModel engineModel;
    private static EngineRuleModel ruleModel;

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
        ruleModel = EngineRuleModel.builder()
                .id(1L)
                .engine(engineModel)
                .title("titulo")
                .description("descricao")
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
    void createEngineRuleSuccessfully() throws Exception {
        //Arrange
        RuleCreateRequestDto requestDto = RuleCreateRequestDto.builder()
                .idSys(1L)
                .title("titulo")
                .description("descricao")
                .build();
        String requestBody = new ObjectMapper().writeValueAsString(requestDto);

        Mockito.when(userRepository.findByUsername(Mockito.any(String.class)))
                .thenReturn(Optional.of(userModel));
        Mockito.when(engineRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(engineModel));
        Mockito.when(ruleRepository.save(Mockito.any(EngineRuleModel.class)))
                .thenReturn(ruleModel);
        //Act
        String responseJsonString = mockMvc.perform(post("/engine-rule/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        RuleResponseDto result = objectMapper.readValue(responseJsonString, new TypeReference<>(){});

        //Assert
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(requestDto.getTitle(), result.getTitle())
        );
    }

    @Test
    @WithMockUser(username = "testuser", roles = {""})
    void updateEngineRuleSuccessfully() throws Exception {
        //Arrange
        RulePostRequestDto requestDto = RulePostRequestDto.builder()
                .title("titulo")
                .description("description")
                .build();
        String requestBody = new ObjectMapper().writeValueAsString(requestDto);

        Mockito.when(userRepository.findByUsername(Mockito.any(String.class)))
                .thenReturn(Optional.of(userModel));
        Mockito.when(engineRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(engineModel));
        Mockito.when(ruleRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(ruleModel));
        Mockito.when(ruleRepository.save(Mockito.any(EngineRuleModel.class)))
                .thenReturn(ruleModel);
        //Act
        String responseJsonString = mockMvc.perform(put("/engine-rule/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id_regra", String.valueOf(1L))
                        .content(requestBody))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        RuleResponseDto result = objectMapper.readValue(responseJsonString, new TypeReference<>(){});

        //Assert
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(requestDto.getTitle(), result.getTitle())
        );
    }

    @Test
    @WithMockUser(username = "testuser", roles = {""})
    void deleteEngineRuleSuccessfully() throws Exception {
        // Arrange:
        Mockito.when(userRepository.findByUsername(Mockito.any(String.class)))
                .thenReturn(Optional.of(userModel));
        Mockito.when(engineRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(engineModel));
        Mockito.when(ruleRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(ruleModel));
        Mockito.doNothing().when(ruleRepository).delete(Mockito.any(EngineRuleModel.class));
        // Act:
        mockMvc.perform(delete("/engine-rule/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id_regra", String.valueOf(1L))
                )
                .andExpect(status().isOk())
                .andExpect(content().string("Rule deleted successfully!"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {""})
    void getEngineRuleSuccessfully() throws Exception {
        // Arrange:
        Mockito.when(userRepository.findByUsername(Mockito.any(String.class)))
                .thenReturn(Optional.of(userModel));
        Mockito.when(engineRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(engineModel));
        Mockito.when(ruleRepository.findAllFiltered(Mockito.any(Long.class), Mockito.any(Long.class)))
                .thenReturn(List.of(ruleModel));
        // Act:
        String responseJsonString = mockMvc.perform(get("/engine-rule/get")
                        .param("id_regra", String.valueOf(1L))
                        .param("id_sistema", String.valueOf(1L))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        List<RuleResponseDto> result = objectMapper.readValue(responseJsonString, new TypeReference<>(){});

        // Assert:
        assertAll(
                () -> assertNotNull(result)
        );
    }

}
