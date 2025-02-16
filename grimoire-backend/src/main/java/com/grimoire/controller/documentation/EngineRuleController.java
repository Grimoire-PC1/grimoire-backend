package com.grimoire.controller.documentation;

import com.grimoire.dto.engineRule.RuleResponseDto;
import com.grimoire.dto.engineRule.RuleCreateRequestDto;
import com.grimoire.dto.engineRule.RuleEditRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Regras de Sistema", description = "Serviço de Regras de Sistemas")
public interface EngineRuleController {
    @Operation(description = "Registrar Regras de sistema", summary = "Registrar nova Regra de Sistema de RPG no Sistema de Aplicativo")
    ResponseEntity<String> createRule(
            @RequestBody RuleCreateRequestDto engineDto,
            Authentication authentication);

    @Operation(description = "Atualizar Regras de sistema", summary = "Atualizar Regras de Sistema de RPG no Sistema de Aplicativo. Deixe o campo em branco para mantê-lo.")
    ResponseEntity<String> updateRule(
            @RequestBody() RuleEditRequestDto userDto,
            String title, Long idUser);

    @Operation(description = "Deletar Regras de sistema", summary = "Remover Regras de Sistema de RPG no Sistema de Aplicativo.")
    ResponseEntity<String> deleteRule(String title, Long idUser);

    @Operation(description = "Ler Regras de usuário", summary = "Pegar informações de Regras de Sistema de RPG no Sistema de Aplicativo.")
    ResponseEntity<RuleResponseDto> getRule(String title, Long idSys);
}
