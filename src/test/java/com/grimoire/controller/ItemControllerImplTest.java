package com.grimoire.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.grimoire.dto.engine.EngineTypeEnum;
import com.grimoire.dto.item.ItemCreateRequestDto;
import com.grimoire.dto.item.ItemPostRequestDto;
import com.grimoire.dto.item.ItemResponseDto;
import com.grimoire.model.grimoire.*;
import com.grimoire.model.grimoire.typeTables.EngineTypeModel;
import com.grimoire.repository.CampaignRepository;
import com.grimoire.repository.ItemRepository;
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
public class ItemControllerImplTest {

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
    private ItemRepository itemRepository;


    private static UserModel userModel;
    private static EngineModel engineModel;
    private static CampaignModel campaignModel;
    private static ItemModel itemModel;

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
        itemModel = ItemModel.builder()
                .id(1L)
                .campaign(campaignModel)
                .name("Espada Longa")
                .description("Uma espada longa afiada")
                .diceQuantity(1)
                .diceType(8)
                .diceBonus(2)
                .rollDice("1d8+2")
                .itemQuantity(2)
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
    void createItemSuccessfully() throws Exception {
        //Arrange
        ItemCreateRequestDto requestDto = ItemCreateRequestDto.builder()
                .name("Espada Longa")
                .description("Uma espada longa afiada")
                .diceQuantity(1)
                .diceType(8)
                .diceBonus(2)
                .rollDice("1d8+2")
                .itemQuantity(1)
                .build();
        String requestBody = new ObjectMapper().writeValueAsString(requestDto);

        Mockito.when(userRepository.findByUsername(Mockito.any(String.class)))
                .thenReturn(Optional.of(userModel));
        Mockito.when(campaignRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(campaignModel));
        Mockito.when(itemRepository.save(Mockito.any(ItemModel.class)))
                .thenReturn(itemModel);
        //Act
        String responseJsonString = mockMvc.perform(post("/item/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id_campanha", String.valueOf(1L))
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        ItemResponseDto result = objectMapper.readValue(responseJsonString, new TypeReference<>(){});

        //Assert
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(requestDto.getName(), result.getName())
        );
    }

    @Test
    @WithMockUser(username = "testuser", roles = {""})
    void updateItemSuccessfully() throws Exception {
        //Arrange
        ItemPostRequestDto requestDto = ItemPostRequestDto.builder()
                .name("Espada Longa")
                .description("Uma espada longa afiada")
                .diceQuantity(1)
                .diceType(8)
                .diceBonus(2)
                .rollDice("1d8+2")
                .itemQuantity(1)
                .build();
        String requestBody = new ObjectMapper().writeValueAsString(requestDto);

        Mockito.when(userRepository.findByUsername(Mockito.any(String.class)))
                .thenReturn(Optional.of(userModel));
        Mockito.when(campaignRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(campaignModel));
        Mockito.when(itemRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(itemModel));
        Mockito.when(itemRepository.save(Mockito.any(ItemModel.class)))
                .thenReturn(itemModel);
        //Act
        String responseJsonString = mockMvc.perform(put("/item/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id_item", String.valueOf(1L))
                        .content(requestBody))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        ItemResponseDto result = objectMapper.readValue(responseJsonString, new TypeReference<>(){});

        //Assert
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(requestDto.getName(), result.getName())
        );
    }

    @Test
    @WithMockUser(username = "testuser", roles = {""})
    void deleteItemSuccessfully() throws Exception {
        // Arrange:
        Mockito.when(userRepository.findByUsername(Mockito.any(String.class)))
                .thenReturn(Optional.of(userModel));
        Mockito.when(campaignRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(campaignModel));
        Mockito.when(itemRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(itemModel));
        Mockito.doNothing().when(itemRepository).delete(Mockito.any(ItemModel.class));
        // Act:
        mockMvc.perform(delete("/item/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id_item", String.valueOf(1L))
                )
                .andExpect(status().isOk())
                .andExpect(content().string("Item deleted successfully!"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {""})
    void getByCampaignItemSuccessfully() throws Exception {
        // Arrange:
        Mockito.when(userRepository.findByUsername(Mockito.any(String.class)))
                .thenReturn(Optional.of(userModel));
        Mockito.when(campaignRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(campaignModel));
        Mockito.when(itemRepository.findAllByCampaign(1L))
                .thenReturn(List.of(itemModel));

        // Act:
        String responseJsonString = mockMvc.perform(get("/item/get")
                        .param("id_campanha", String.valueOf(1L))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        List<ItemResponseDto> result = objectMapper.readValue(responseJsonString, new TypeReference<>() {});

        // Assert:
        assertAll(
                () -> assertNotNull(result)
        );
    }

}
