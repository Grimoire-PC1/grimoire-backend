package com.grimoire.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.grimoire.dto.campaign.CampaignCreateRequestDto;
import com.grimoire.dto.campaign.CampaignPostRequestDto;
import com.grimoire.dto.campaign.CampaignResponseDto;
import com.grimoire.dto.user.UserCreateRequestDto;
import com.grimoire.dto.user.UserPostRequestDto;
import com.grimoire.dto.user.UserResponseDto;
import com.grimoire.model.grimoire.CampaignModel;
import com.grimoire.model.grimoire.UserModel;
import com.grimoire.repository.CampaignRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;


import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@Import(CampaignControllerImpl.class)
public class CampaignControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CampaignRepository campaignRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static MockRestServiceServer mockServer;

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
    @WithMockUser(username = "testUser", roles = {"USER"})
    void createCampaignSuccessfully() throws Exception {
        CampaignCreateRequestDto requestDto = CampaignCreateRequestDto.builder()
                .idMaster(2147483647L)
                .title("title")
                .description("description")
                .idSystem(1024L)
                .build();
        String requestBody = new ObjectMapper().writeValueAsString(requestDto);

        Mockito.when(campaignRepository.existsByTitle(Mockito.any(String.class)))
                .thenReturn(false);
        Mockito.when(campaignRepository.save(Mockito.any(CampaignModel.class)))
                .thenReturn(new CampaignModel());

        String responseJsonString = mockMvc.perform(post("/campaign/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().string("Campaign registered successfully!"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER"})
    void updateCampaignSuccessfully() throws Exception {
        CampaignCreateRequestDto requestDto = CampaignCreateRequestDto.builder()
                .idMaster(2147483647L)
                .title("title")
                .description("description")
                .idSystem(1024L)
                .pictureUrl("")
                .build();
        String requestBody = new ObjectMapper().writeValueAsString(requestDto);

        Mockito.when(campaignRepository.findByTitle(Mockito.any(String.class)))
                .thenReturn(Optional.of(new CampaignModel()));
        Mockito.when(campaignRepository.save(Mockito.any(CampaignModel.class)))
                .thenReturn(new CampaignModel());

        mockMvc.perform(put("/campaign/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )
                .andExpect(status().isOk())
                .andExpect(content().string("Campaign updated successfully!"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER"})
    void deleteCampaignSuccessfully() throws Exception {
        Mockito.when(campaignRepository.findByTitle(Mockito.any(String.class)))
                .thenReturn(Optional.of(new CampaignModel()));
        Mockito.doNothing().when(campaignRepository).delete(Mockito.any(CampaignModel.class));

        mockMvc.perform(delete("/campaign/delete")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("Campaign deleted successfully!"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER"})
    void getCampaignSuccessfully() throws Exception {
        CampaignModel campaign = CampaignModel.builder()
                .idMaster(2147483647L)
                .title("title")
                .description("description")
                .idSystem(1024L)
                .pictureUrl("url")
                .build();
        CampaignResponseDto responseDto = campaign.toDto();
        String responseBody = new ObjectMapper().writeValueAsString(responseDto);

        Mockito.when(campaignRepository.findByTitle(Mockito.any(String.class)))
                .thenReturn(Optional.of(campaign));

        mockMvc.perform(get("/campaign/get")
                )
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody))
                .andDo(print());
    }

}
