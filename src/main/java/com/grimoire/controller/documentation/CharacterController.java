package com.grimoire.controller.documentation;

import com.grimoire.dto.character.CharacterCreateRequestDto;
import com.grimoire.dto.character.CharacterPostRequestDto;
import com.grimoire.dto.character.CharacterResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;

@Tag(name = "Personagem", description = "Serviço de Personagem")
public interface CharacterController {
    @Operation(description = "Registrar Personagem", summary = "Registrar novo Personagem de Campanha de RPG no Grimoire")
    ResponseEntity<CharacterResponseDto> create(
            @Parameter(
                    name = "id_campanha",
                    description = "ID da Campanha de RPG.",
                    required = true
            )
            Long campaignId,
            @Validated @RequestBody CharacterCreateRequestDto characterDto,
            Authentication authentication);

    @Operation(description = "Atualizar Personagem", summary = "Atualizar Personagem de Campanha de RPG no Grimoire")
    ResponseEntity<CharacterResponseDto> update(
            @Parameter(
                    name = "id_personagem",
                    description = "ID do Personagem de RPG.",
                    required = true
            )
            Long characterId,
            @Validated @RequestBody CharacterPostRequestDto characterDto,
            Authentication authentication);

    @Operation(description = "Remover Personagem", summary = "Remover Personagem de Campanha de RPG no Grimoire.")
    ResponseEntity<String> delete(
            @Parameter(
                    name = "id_personagem",
                    description = "ID do Personagem de RPG.",
                    required = true
            )
            Long characterId,
            Authentication authentication);

    @Operation(description = "Pegar Personagens do Usuário", summary = "Pegar informações de Personagens do usuário no Grimoire.")
    ResponseEntity<Collection<CharacterResponseDto>> getByUser(
            Authentication authentication);

    @Operation(description = "Pegar Personagens da Campanha", summary = "Pegar informações de Personagens de Campanha de RPG no Grimoire.")
    ResponseEntity<Collection<CharacterResponseDto>> getByCampaign(
            @Parameter(
                    name = "id_campanha",
                    description = "ID da Campanha de RPG.",
                    required = true
            )
            Long campaignId,
            Authentication authentication);
}
