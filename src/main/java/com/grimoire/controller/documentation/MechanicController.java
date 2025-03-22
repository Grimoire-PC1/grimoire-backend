package com.grimoire.controller.documentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.grimoire.dto.characterSheetTemplate.CharacterSheetTabResponseDto;
import com.grimoire.dto.mechanic.MechanicCreateRequestDto;
import com.grimoire.dto.mechanic.MechanicPostRequestDto;
import com.grimoire.dto.mechanic.MechanicResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;

@Tag(name = "Mecânica", description = "Serviço de Mecânicas de Sistema")
public interface MechanicController {

    @Operation(description = "Registrar Mecânica", summary = "Registrar nova Mecânica de Sistema de RPG no Grimoire")
    ResponseEntity<MechanicResponseDto> create(
            @Parameter(
                    name = "id_sistema",
                    description = "ID do Sistema de RPG.",
                    required = true
            )
            Long engineId,
            @Validated @RequestBody MechanicCreateRequestDto mechanicDto,
            Authentication authentication) throws JsonProcessingException;

    @Operation(description = "Atualizar Mecânica", summary = "Atualizar Mecânica de RPG no Grimoire")
    ResponseEntity<MechanicResponseDto> update(
            @Parameter(
                    name = "id_mecanica",
                    description = "ID da Mecânica de RPG.",
                    required = true
            )
            Long mechanicId,
            @Validated @RequestBody MechanicPostRequestDto mechanicDto,
            Authentication authentication) throws JsonProcessingException;

    @Operation(description = "Remover Mecânica", summary = "Remover Mecânica de RPG no Grimoire.")
    ResponseEntity<String> delete(
            @Parameter(
                    name = "id_mecanica",
                    description = "ID da Mecânica de RPG.",
                    required = true
            )
            Long mechanicId,
            Authentication authentication);

    @Operation(description = "Pegar Mecânicas", summary = "Pegar informações de Mecânicas de RPG no Grimoire.")
    ResponseEntity<Collection<MechanicResponseDto>> get(
            @Parameter(
                    name = "id_sistema",
                    description = "ID do Sistema de RPG.",
                    required = true
            )
            Long engineId,
            Authentication authentication);

    @Operation(description = "Pegar Mecânicas por Campanha", summary = "Pegar informações de Mecânicas de RPG no Grimoire.")
    ResponseEntity<Collection<MechanicResponseDto>> getByCampaign(
            @Parameter(
                    name = "id_campanha",
                    description = "ID da Campanha de RPG.",
                    required = true
            )
            Long campaignId,
            Authentication authentication);
}
