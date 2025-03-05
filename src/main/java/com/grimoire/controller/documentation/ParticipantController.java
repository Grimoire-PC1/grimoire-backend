package com.grimoire.controller.documentation;

import com.grimoire.dto.participant.ParticipantResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.Collection;

@Tag(name = "Participante", description = "Serviço de Participantes de Campanha")
public interface ParticipantController {

    @Operation(description = "Registrar Participante de Campanha", summary = "Registrar novo Participante de Campanha de RPG no Grimoire")
    ResponseEntity<ParticipantResponseDto> create(
            @Parameter(
                    name = "id_campanha",
                    description = "ID da Campanha de RPG.",
                    required = true
            )
            Long campaignId,
            Authentication authentication);

    @Operation(description = "Remover Participante de Campanha", summary = "Remover Participante de Campanha de RPG no Grimoire.")
    ResponseEntity<String> delete(
            @Parameter(
                    name = "id_campanha",
                    description = "ID da Campanha de RPG.",
                    required = true
            )
            Long campaignId,
            Authentication authentication);

    @Operation(description = "Pegar Participante de Campanha", summary = "Pegar informações de Participantes de Campanha de RPG no Grimoire.")
    ResponseEntity<Collection<ParticipantResponseDto>> get(
            @Parameter(
                    name = "id_campanha",
                    description = "ID da Campanha de RPG.",
                    required = true
            )
            Long campaignId,
            Authentication authentication);
}
