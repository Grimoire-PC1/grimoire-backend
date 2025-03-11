package com.grimoire.controller.documentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.grimoire.dto.characterSheetContent.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;

@Tag(name = "Personagem: Conteúdo de Ficha", description = "Serviço de Conteúdo Ficha de Personagem de Campanha")
public interface CharacterSheetContentController {

    @Operation(description = "Registrar Conteúdo de Ficha", summary = "Registrar nova Conteúdo de Ficha de Personagem de Campanha de RPG no Grimoire")
    ResponseEntity<CharacterSheetContentResponseDto> create(
            @Parameter(
                    name = "id_personagem",
                    description = "ID do Personagem de RPG.",
                    required = true
            )
            Long characterId,
            @Parameter(
                    name = "id_sub_aba_ficha",
                    description = "ID da Aba de Ficha de RPG.",
                    required = true
            )
            Long characterSheetSubTabId,
            @Validated @RequestBody CharacterSheetContentCreateRequestDto characterSheetDto,
            Authentication authentication) throws JsonProcessingException;

    @Operation(description = "Atualizar Conteúdo de Ficha", summary = "Atualizar Conteúdo de Ficha de RPG no Grimoire")
    ResponseEntity<CharacterSheetContentResponseDto> update(
            @Parameter(
                    name = "id_ficha_Content",
                    description = "ID da Conteúdo de Ficha de RPG.",
                    required = true
            )
            Long characterSheetContentId,
            @Validated @RequestBody CharacterSheetContentPostRequestDto characterSheetDto,
            Authentication authentication) throws JsonProcessingException;

    @Operation(description = "Remover Conteúdo de Ficha", summary = "Remover Conteúdo de Ficha de RPG no Grimoire.")
    ResponseEntity<String> delete(
            @Parameter(
                    name = "id_ficha_Content",
                    description = "ID da Conteúdo de Ficha de RPG.",
                    required = true
            )
            Long characterSheetContentId,
            Authentication authentication);

    @Operation(description = "Pegar Conteúdo de Fichas", summary = "Pegar Contentrmações de Fichas de RPG no Grimoire.")
    ResponseEntity<Collection<CharacterSheetContentResponseDto>> get(
            @Parameter(
                    name = "id_personagem",
                    description = "ID do Personagem de RPG.",
                    required = true
            )
            Long characterId,
            @Parameter(
                    name = "id_aba_ficha",
                    description = "ID da Aba de Ficha de RPG.",
                    required = false
            )
            Long characterSheetTabId,
            @Parameter(
                    name = "id_sub_aba_ficha",
                    description = "ID da Sub Aba de Ficha de RPG.",
                    required = true
            )
            Long characterSheetSubTabId,
            Authentication authentication);
}
