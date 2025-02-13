package com.grimoire.service.service;

import com.grimoire.dto.engineRule.RuleResponseDto;
import com.grimoire.dto.engineRule.RuleCreateRequestDto;
import com.grimoire.dto.engineRule.RuleEditRequestDto;

public interface EngineRuleService {
    String createRule(String username, RuleCreateRequestDto engineDTO);
    String editRule(String title, Long idUser, RuleEditRequestDto request);
    String deleteRule(String title, Long idUser);
    RuleResponseDto getRule(String title, Long idRule);

}
