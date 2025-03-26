package com.grimoire.service.interfaces;

import com.grimoire.dto.engineRule.RuleResponseDto;
import com.grimoire.dto.engineRule.RuleCreateRequestDto;
import com.grimoire.dto.engineRule.RulePostRequestDto;

import java.util.Collection;

public interface EngineRuleService {
    RuleResponseDto createRule(RuleCreateRequestDto ruleDTO, String username);
    RuleResponseDto editRule(Long ruleId, RulePostRequestDto ruleDto, String username);
    String deleteRule(Long ruleId, String username);
    Collection<RuleResponseDto> getRules(Long idRule, Long idEngine, String username);

    Collection<RuleResponseDto> getByCampaign(Long campaignId, String username);
}
