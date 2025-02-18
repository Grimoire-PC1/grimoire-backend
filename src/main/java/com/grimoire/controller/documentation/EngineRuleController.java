package com.grimoire.controller.documentation;

import com.grimoire.dto.engineRule.RuleResponseDto;
import com.grimoire.dto.engineRule.RuleCreateRequestDto;
import com.grimoire.dto.engineRule.RuleEditRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;

@Tag(name = "Regras de Sistema", description = "Serviço de Regras de Sistemas")
public interface EngineRuleController {
    @Operation(description = "Registrar Regras de sistema", summary = "Registrar nova Regra de Sistema de RPG no Grimoire")
    ResponseEntity<RuleResponseDto> createRule(
            @RequestBody
            RuleCreateRequestDto engineDto,
            Authentication authentication);

    @Operation(description = "Atualizar Regras de sistema", summary = "Atualizar Regras de Sistema de RPG no Grimoire. Deixe o campo em branco para mantê-lo.")
    ResponseEntity<RuleResponseDto> updateRule(
            @Parameter(
                    name = "id_regra",
                    description = "ID da regra de Sistema de RPG.",
                    required = true
            )
            Long ruleId,
            @RequestBody()
            RuleEditRequestDto userDto,
            Authentication authentication);

    @Operation(description = "Deletar Regras de sistema", summary = "Remover Regras de Sistema de RPG no Grimoire.")
    ResponseEntity<String> deleteRule(
            @Parameter(
                    name = "id_regra",
                    description = "ID da regra de Sistema de RPG.",
                    required = true
            )
            Long ruleId,
            Authentication authentication);

    @Operation(description = "Ler Regras do usuário", summary = "Pegar informações de Regras de Sistema de RPG no Grimoire.")
    ResponseEntity<Collection<RuleResponseDto>> getUserRules(
            @Parameter(
                    name = "id_regra",
                    description = "ID da regra de Sistema de RPG."
            )
            Long ruleId,
            @Parameter(
                    name = "id_sistema",
                    description = "ID do Sistema de RPG."
            )
            Long systemId,
            Authentication authentication);
}
