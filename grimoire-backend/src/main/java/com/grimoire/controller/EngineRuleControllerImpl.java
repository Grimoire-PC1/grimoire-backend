package com.grimoire.controller;

import com.grimoire.controller.documentation.EngineRuleController;
import com.grimoire.dto.engineRule.RuleResponseDto;
import com.grimoire.dto.engineRule.RuleCreateRequestDto;
import com.grimoire.dto.engineRule.RuleEditRequestDto;
import com.grimoire.service.service.AuthService;
import com.grimoire.service.service.EngineRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/engine")
public class EngineRuleControllerImpl implements EngineRuleController {
    private final EngineRuleService engineRuleService;
    @Autowired
    public EngineRuleControllerImpl(EngineRuleService engineRuleService, AuthService authService) {
        this.engineRuleService = engineRuleService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> createRule(
            @Validated @RequestBody RuleCreateRequestDto ruleDto,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED).body(engineRuleService.createRule(authentication.getName(), ruleDto));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateRule(
            @Validated @RequestBody RuleEditRequestDto ruleDto,
            String title, Long idUser) {
        return ResponseEntity.status(HttpStatus.OK).body(engineRuleService.editRule(title, idUser, ruleDto));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteRule(
            String title, Long idUser) {
        return ResponseEntity.status(HttpStatus.OK).body(engineRuleService.deleteRule(title, idUser));
    }

    @GetMapping("/get")
    public ResponseEntity<RuleResponseDto> getRule(
            String title, Long idSys) {
        return ResponseEntity.status(HttpStatus.OK).body(engineRuleService.getRule(title, idSys));
    }
}
