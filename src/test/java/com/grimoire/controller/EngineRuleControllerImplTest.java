package com.grimoire.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.grimoire.dto.engine.EngineTypeEnum;
import com.grimoire.model.grimoire.EngineModel;
import com.grimoire.model.grimoire.EngineRuleModel;
import com.grimoire.model.grimoire.UserModel;
import com.grimoire.model.grimoire.typeTables.EngineTypeModel;
import com.grimoire.repository.EngineRepository;
import com.grimoire.repository.UserRepository;
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
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class EngineRuleControllerImplTest {

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
    private static EngineRuleModel engineRuleModel;

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
        engineRuleModel = EngineRuleModel.builder()
                .id(1L)
                .engine(engineModel)
                .title("titulo")
                .description("description")
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
    void createEngineRuleSuccessfully() throws Exception {}

    @Test
    @WithMockUser(username = "testuser", roles = {""})
    void updateEngineRuleSuccessfully() throws Exception {}

    @Test
    @WithMockUser(username = "testuser", roles = {""})
    void deleteEngineRuleSuccessfully() throws Exception {}

    @Test
    @WithMockUser(username = "testuser", roles = {""})
    void getEngineRuleSuccessfully() throws Exception {}

}
