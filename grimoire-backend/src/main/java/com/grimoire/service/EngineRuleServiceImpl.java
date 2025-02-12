package com.grimoire.service;

import com.grimoire.dto.engineRule.RuleResponseDto;
import com.grimoire.dto.engineRule.RuleCreateRequestDto;
import com.grimoire.dto.engineRule.RuleEditRequestDto;
import com.grimoire.model.joinTables.EngineRuleModel;
import com.grimoire.repository.EngineRuleRepository;
import com.grimoire.service.service.EngineRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EngineRuleServiceImpl implements EngineRuleService {

    private final EngineRuleRepository engineRuleRepository;

    @Autowired
    public EngineRuleServiceImpl(EngineRuleRepository engineRuleRepository, PasswordEncoder passwordEncoder) {
        this.engineRuleRepository = engineRuleRepository;
    }

    @Override
    @Transactional
    public String createRule(RuleCreateRequestDto ruleDTO) {
        if (engineRuleRepository.existsByName(ruleDTO.getTitle())) {
            throw new RuntimeException("Rule already exists!");
        }
        EngineRuleModel rule = EngineRuleModel.builder()
                .idUser(ruleDTO.getIdUser())
                .idSys(ruleDTO.getIdSys())
                .title(ruleDTO.getTitle())
                .description(ruleDTO.getDescription())
                .type(ruleDTO.getType())
                .build();

        engineRuleRepository.save(rule);
        return "Rule registered successfully!";
    }

    @Override
    @Transactional
    public String editRule(String title, RuleEditRequestDto ruleDTO) {
        EngineRuleModel rule = engineRuleRepository.findByName(title)
                .orElseThrow(() -> new IllegalArgumentException("Rule not found: " + title));
        rule.setTitle(ruleDTO.getTitle().isBlank() ? rule.getTitle() : ruleDTO.getTitle());
        rule.setDescription(ruleDTO.getDescription().isBlank() ? rule.getDescription() : ruleDTO.getDescription());
        rule.setType(ruleDTO.getType().isBlank() ? rule.getType() : ruleDTO.getType());

        engineRuleRepository.save(rule);
        return "Rule updated successfully!";
    }

    @Override
    @Transactional
    public String deleteRule(String title) {
        EngineRuleModel rule = engineRuleRepository.findByName(title)
                .orElseThrow(() -> new IllegalArgumentException("Rule not found: " + title));

        engineRuleRepository.delete(rule);
        return "Rule deleted successfully!";
    }

    @Override
    public RuleResponseDto getRule(String title) {
        EngineRuleModel rule = engineRuleRepository.findByName(title)
                .orElseThrow(() -> new IllegalArgumentException("Rule not found: " + title));

        return rule.toDto();
    }
}
