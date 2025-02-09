package com.grimoire.controller.documentation;

import com.grimoire.dto.engine.EngineResponseDto;
import com.grimoire.dto.engine.EngineEditRequestDto;
import com.grimoire.dto.engine.EngineCreateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Sistema", description = "Serviço de Sistemas")
public interface EngineController {
    @Operation(description = "Registrar sistema", summary = "Registrar novo Sistema de RPG no Sistema de Aplicativo")
    ResponseEntity<String> createEngine(@Validated @RequestBody EngineCreateRequestDto engineDto);

    @Operation(description = "Atualizar sistema", summary = "Atualizar Sistema de RPG no Sistema de Aplicativo. Deixe o campo em branco para mantê-lo.")
    ResponseEntity<String> updateEngine(
            @Validated @RequestBody() EngineEditRequestDto userDto,
            Authentication authentication);

    @Operation(description = "Deletar sistema", summary = "Remover Sistema de RPG no Sistema de Aplicativo.")
    ResponseEntity<String> deleteEngine(Authentication authentication);

    @Operation(description = "Ler usuário", summary = "Pegar informações do Sistema de RPG no Sistema de Aplicativo.")
    ResponseEntity<EngineResponseDto> getEngine(Authentication authentication);
}
