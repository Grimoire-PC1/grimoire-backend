package com.grimoire.service.service;

import com.grimoire.dto.engineRule.RuleResponseDto;
import com.grimoire.dto.engineRule.RuleCreateRequestDto;
import com.grimoire.dto.engineRule.RuleEditRequestDto;

import java.util.Collection;

public interface EngineRuleService {
    RuleResponseDto createRule(RuleCreateRequestDto ruleDTO, String username);
    RuleResponseDto editRule(Long ruleId, RuleEditRequestDto ruleDto, String username);
    String deleteRule(Long ruleId, String username);
    Collection<RuleResponseDto> getRules(Long idRule, Long idEngine, String username);

}
