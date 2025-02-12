package com.grimoire.service.service;

import com.grimoire.dto.engineRule.RuleResponseDto;
import com.grimoire.dto.engineRule.RuleCreateRequestDto;
import com.grimoire.dto.engineRule.RuleEditRequestDto;

public interface EngineRuleService {
    String createRule(RuleCreateRequestDto engineDTO);
    String editRule(String name, RuleEditRequestDto request);
    String deleteRule(String name);
    RuleResponseDto getRule(String name);

}
