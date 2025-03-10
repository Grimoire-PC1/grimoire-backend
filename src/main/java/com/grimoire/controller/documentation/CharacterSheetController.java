package com.grimoire.controller.documentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.grimoire.dto.characterSheet.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;

@Tag(name = "Template de Ficha de Personagem", description = "Serviço de Template Ficha de Personagem de Sistema")
public interface CharacterSheetController {

    @Operation(description = "Registrar Aba de Ficha", summary = "Registrar nova Aba de Ficha de Personagem de Sistema de RPG no Grimoire")
    ResponseEntity<CharacterSheetTabResponseDto> createTab(
            @Parameter(
                    name = "id_sistema",
                    description = "ID do Sistema de RPG.",
                    required = true
            )
            Long engineId,
            @Validated @RequestBody CharacterSheetTabCreateRequestDto characterSheetTabDto,
            Authentication authentication) throws JsonProcessingException;

    @Operation(description = "Atualizar Aba de Ficha", summary = "Atualizar Aba de Ficha de RPG no Grimoire")
    ResponseEntity<CharacterSheetTabResponseDto> updateTab(
            @Parameter(
                    name = "id_aba_ficha",
                    description = "ID da Aba de Ficha de RPG.",
                    required = true
            )
            Long characterSheetTabId,
            @Validated @RequestBody CharacterSheetTabPostRequestDto characterSheetTabDto,
            Authentication authentication) throws JsonProcessingException;

    @Operation(description = "Remover Aba de Ficha", summary = "Remover Aba de Ficha de RPG no Grimoire.")
    ResponseEntity<String> deleteTab(
            @Parameter(
                    name = "id_aba_ficha",
                    description = "ID da Aba de Ficha de RPG.",
                    required = true
            )
            Long characterSheetTabId,
            Authentication authentication);

    @Operation(description = "Pegar Abas de Fichas", summary = "Pegar informações de Abas de Fichas de RPG no Grimoire.")
    ResponseEntity<Collection<CharacterSheetTabResponseDto>> getTab(
            @Parameter(
                    name = "id_sistema",
                    description = "ID do Sistema de RPG.",
                    required = true
            )
            Long engineId,
            Authentication authentication);

    @Operation(description = "Pegar Abas de Fichas por Campanha", summary = "Pegar informações de Abas de Fichas de RPG no Grimoire.")
    ResponseEntity<Collection<CharacterSheetTabResponseDto>> getTabByCampaign(
            @Parameter(
                    name = "id_campanha",
                    description = "ID da Campanha de RPG.",
                    required = true
            )
            Long campaignId,
            Authentication authentication);

    @Operation(description = "Registrar Sub Aba de Ficha", summary = "Registrar nova Sub Aba de Ficha de Personagem de Sistema de RPG no Grimoire")
    ResponseEntity<CharacterSheetSubTabResponseDto> createSubTab(
            @Parameter(
                    name = "id_aba_ficha",
                    description = "ID da Aba de Ficha de RPG.",
                    required = true
            )
            Long characterSheetTabId,
            @Parameter(
                    name = "tipo_sub_aba",
                    description = "Tipo de Sub Aba.",
                    required = true
            )
            CharacterSheetSubTabTypeEnum characterSheetSubTabTypeEnum,
            @Validated @RequestBody CharacterSheetSubTabCreateRequestDto characterSheetSubTabDto,
            Authentication authentication) throws JsonProcessingException;

    @Operation(description = "Atualizar Sub Aba de Ficha", summary = "Atualizar Sub Aba de Ficha de RPG no Grimoire")
    ResponseEntity<CharacterSheetSubTabResponseDto> updateSubTab(
            @Parameter(
                    name = "id_sub_aba_ficha",
                    description = "ID da Sub Aba de Ficha de RPG.",
                    required = true
            )
            Long characterSheetSubTabId,
            @Validated @RequestBody CharacterSheetSubTabPostRequestDto characterSheetSubTabDto,
            Authentication authentication) throws JsonProcessingException;

    @Operation(description = "Remover Aba de Ficha", summary = "Remover Aba de Ficha de RPG no Grimoire.")
    ResponseEntity<String> deleteSubTab(
            @Parameter(
                    name = "id_sub_aba_ficha",
                    description = "ID da Sub Aba de Ficha de RPG.",
                    required = true
            )
            Long characterSheetSubTabId,
            Authentication authentication);

    @Operation(description = "Pegar Sub Abas de Fichas", summary = "Pegar informações de Sub Abas de Fichas de RPG no Grimoire.")
    ResponseEntity<Collection<CharacterSheetSubTabResponseDto>> getSubTab(
            @Parameter(
                    name = "id_sistema",
                    description = "ID do Sistema de RPG.",
                    required = true
            )
            Long engineId,
            @Parameter(
                    name = "id_aba_ficha",
                    description = "ID da Aba de Ficha de RPG.",
                    required = false
            )
            Long characterSheetTabId,
            Authentication authentication);

    @Operation(description = "Pegar Sub Abas de Fichas por Campanha", summary = "Pegar informações de Sub Abas de Fichas de RPG no Grimoire.")
    ResponseEntity<Collection<CharacterSheetSubTabResponseDto>> getSubTabByCampaign(
            @Parameter(
                    name = "id_campanha",
                    description = "ID da Campanha de RPG.",
                    required = true
            )
            Long campaignId,
            @Parameter(
                    name = "id_aba_ficha",
                    description = "ID da Aba de Ficha de RPG.",
                    required = false
            )
            Long characterSheetTabId,
            Authentication authentication);
}
