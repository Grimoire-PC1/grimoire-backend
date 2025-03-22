package com.grimoire.controller;

import com.grimoire.controller.documentation.EngineRuleController;
import com.grimoire.dto.characterSheetTemplate.CharacterSheetTabResponseDto;
import com.grimoire.dto.engineRule.RuleResponseDto;
import com.grimoire.dto.engineRule.RuleCreateRequestDto;
import com.grimoire.dto.engineRule.RulePostRequestDto;
import com.grimoire.service.interfaces.EngineRuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/engine-rule")
@Validated
@CrossOrigin
@Slf4j
public class EngineRuleControllerImpl implements EngineRuleController {
    private final EngineRuleService engineRuleService;
    @Autowired
    public EngineRuleControllerImpl(EngineRuleService engineRuleService) {
        this.engineRuleService = engineRuleService;
    }

    @PostMapping("/register")
    public ResponseEntity<RuleResponseDto> createRule(
            @Validated @RequestBody RuleCreateRequestDto ruleDto,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED).body(engineRuleService.createRule(ruleDto, authentication.getName()));
    }

    @PutMapping("/update")
    public ResponseEntity<RuleResponseDto> updateRule(
            @RequestParam(name = "id_regra") Long ruleId,
            @Validated @RequestBody RulePostRequestDto ruleDto,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(engineRuleService.editRule(ruleId, ruleDto, authentication.getName()));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteRule(
            @RequestParam(name = "id_regra") Long ruleId,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(engineRuleService.deleteRule(ruleId, authentication.getName()));
    }

    @GetMapping("/get")
    public ResponseEntity<Collection<RuleResponseDto>> getUserRules(
            @RequestParam(name = "id_regra", required = false) Long ruleId,
            @RequestParam(name = "id_sistema") Long systemId,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(engineRuleService.getRules(ruleId, systemId, authentication.getName()));
    }

    @Override
    @GetMapping("/get/campaign")
    public ResponseEntity<Collection<RuleResponseDto>> getByCampaign(
            @RequestParam(name = "id_campanha") Long campaignId,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(
                engineRuleService.getByCampaign(campaignId, authentication.getName()));
    }
}
