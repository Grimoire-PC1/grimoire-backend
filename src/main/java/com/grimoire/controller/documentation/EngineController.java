package com.grimoire.controller.documentation;

import com.grimoire.dto.engine.EngineResponseDto;
import com.grimoire.dto.engine.EnginePostRequestDto;
import com.grimoire.dto.engine.EngineCreateRequestDto;
import com.grimoire.dto.engine.EngineTypeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;

@Tag(name = "Sistema", description = "Serviço de Sistemas")
public interface EngineController {
    @Operation(description = "Registrar sistema", summary = "Registrar novo Sistema de RPG.")
    ResponseEntity<EngineResponseDto> createEngine(
            @Parameter(
                    name = "tipo_sistema",
                    description = "Visibilidade do Sistema de RPG.",
                    required = true
            )
            EngineTypeEnum engineTypeEnum,
            @RequestBody
            EngineCreateRequestDto engineDto,
            Authentication authentication);

    @Operation(description = "Atualizar sistema", summary = "Atualizar Sistema de RPG. Deixe o campo em branco para mantê-lo.")
    ResponseEntity<EngineResponseDto> updateEngine(
            @Parameter(
                    name = "id_sistema",
                    description = "ID do Sistema de RPG.",
                    required = true
            )
            Long idSys,
            @Parameter(
                    name = "tipo_sistema",
                    description = "Visibilidade do Sistema de RPG."
            )
            EngineTypeEnum engineTypeEnum,
            @RequestBody
            EnginePostRequestDto engineDto,
            Authentication authentication);

    @Operation(description = "Deletar sistema", summary = "Remover Sistema de RPG.")
    ResponseEntity<String> deleteEngine(
            @Parameter(
                    name = "id_sistema",
                    description = "ID do Sistema de RPG.",
                    required = true
            )
            Long idSys,
            Authentication authentication);

    @Operation(description = "Pegar sistemas do usuário", summary = "Pegar informações dos Sistemas de RPG do usuário.")
    ResponseEntity<Collection<EngineResponseDto>> getUserEngine(
            @Parameter(
                    name = "id_sistema",
                    description = "ID do Sistema de RPG."
            )
            Long idSys,
            Authentication authentication);

    @Operation(description = "Pegar sistemas públicos", summary = "Pegar informações dos Sistemas de RPG públicos.")
    ResponseEntity<Collection<EngineResponseDto>> getPublicEngine(
            Authentication authentication);
}
