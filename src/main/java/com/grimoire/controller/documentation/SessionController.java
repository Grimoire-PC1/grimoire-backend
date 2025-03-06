package com.grimoire.controller.documentation;

import com.grimoire.dto.session.SessionCreateRequestDto;
import com.grimoire.dto.session.SessionPostRequestDto;
import com.grimoire.dto.session.SessionResponseDto;
import com.grimoire.dto.session.SessionTypeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;

@Tag(name = "Sessões", description = "Serviço de Sessões de Campanha")
public interface SessionController {
    @Operation(description = "Registrar Sessão", summary = "Registrar nova Sessão de Campanha de RPG no Grimoire")
    ResponseEntity<SessionResponseDto> create(
            @Parameter(
                    name = "id_campanha",
                    description = "ID da Campanha de RPG.",
                    required = true
            )
            Long campaignId,
            @Parameter(
                    name = "tipo_sessao",
                    description = "Status da Sessao de RPG.",
                    required = true
            )
            SessionTypeEnum sessionTypeEnum,
            @Validated @RequestBody SessionCreateRequestDto sessionDto,
            Authentication authentication);

    @Operation(description = "Atualizar Sessão", summary = "Atualizar Sessão de Campanha de RPG no Grimoire")
    ResponseEntity<SessionResponseDto> update(
            @Parameter(
                    name = "id_sessao",
                    description = "ID da Sessão de RPG.",
                    required = true
            )
            Long sessionId,
            @Parameter(
                    name = "novo_tipo_sessao",
                    description = "Status da Sessao de RPG.",
                    required = false
            )
            SessionTypeEnum sessionTypeEnum,
            @Validated @RequestBody SessionPostRequestDto sessionDto,
            Authentication authentication);

    @Operation(description = "Remover Sessão", summary = "Remover Sessão de Campanha de RPG no Grimoire.")
    ResponseEntity<String> delete(
            @Parameter(
                    name = "id_sessao",
                    description = "ID da Sessão de RPG.",
                    required = true
            )
            Long sessionId,
            Authentication authentication);

    @Operation(description = "Pegar Sessões", summary = "Pegar informações de Sessões de Campanha de RPG no Grimoire.")
    ResponseEntity<Collection<SessionResponseDto>> getByCampaign(
            @Parameter(
                    name = "id_campanha",
                    description = "ID da Campanha de RPG.",
                    required = true
            )
            Long campaignId,
            Authentication authentication);
}
