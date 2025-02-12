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
    public ResponseEntity<String> createRule(@Validated @RequestBody RuleCreateRequestDto ruleDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(engineRuleService.createRule(ruleDto));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateRule(
            @Validated @RequestBody RuleEditRequestDto ruleDto,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(engineRuleService.editRule(authentication.getName(), ruleDto));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteRule(
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(engineRuleService.deleteRule(authentication.getName()));
    }

    @GetMapping("/get")
    public ResponseEntity<RuleResponseDto> getRule(
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(engineRuleService.getRule(authentication.getName()));
    }
}
