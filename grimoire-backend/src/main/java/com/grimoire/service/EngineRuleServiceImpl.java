package com.grimoire.service;

import com.grimoire.dto.engineRule.RuleResponseDto;
import com.grimoire.dto.engineRule.RuleCreateRequestDto;
import com.grimoire.dto.engineRule.RuleEditRequestDto;
import com.grimoire.model.grimoire.UserModel;
import com.grimoire.model.grimoire.EngineRuleModel;
import com.grimoire.repository.EngineRuleRepository;
import com.grimoire.repository.UserRepository;
import com.grimoire.service.service.EngineRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EngineRuleServiceImpl implements EngineRuleService {

    private final EngineRuleRepository engineRuleRepository;
    private final UserRepository userRepository;

    @Autowired
    public EngineRuleServiceImpl(EngineRuleRepository engineRuleRepository, UserRepository userRepository) {
        this.engineRuleRepository = engineRuleRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public String createRule(String username, RuleCreateRequestDto ruleDTO) {
        if (engineRuleRepository.existsByTitle(ruleDTO.getTitle())) {
            throw new RuntimeException("Rule already exists!");
        }
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Authorization error"));
        EngineRuleModel rule = EngineRuleModel.builder()
                .title(ruleDTO.getTitle())
                .description(ruleDTO.getDescription())
                .build();

        engineRuleRepository.save(rule);
        return "Rule registered successfully!";
    }

    @Override
    @Transactional
    public String editRule(String title, Long idUser, RuleEditRequestDto ruleDTO) {
        EngineRuleModel rule = engineRuleRepository.findByTitle(title)
                .orElseThrow(() -> new IllegalArgumentException("Rule not found: " + title));
        if (!rule.getEngine().getCreator().getId().equals(idUser)) {
            throw new IllegalArgumentException("You don't have permission to delete System");
        }
        rule.setTitle(ruleDTO.getTitle().isBlank() ? rule.getTitle() : ruleDTO.getTitle());
        rule.setDescription(ruleDTO.getDescription().isBlank() ? rule.getDescription() : ruleDTO.getDescription());

        engineRuleRepository.save(rule);
        return "Rule updated successfully!";
    }

    @Override
    @Transactional
    public String deleteRule(String title, Long idUser) {
        EngineRuleModel rule = engineRuleRepository.findByTitle(title)
                .orElseThrow(() -> new IllegalArgumentException("Rule not found: " + title));
        if (!rule.getEngine().getCreator().getId().equals(idUser)) {
            throw new IllegalArgumentException("You don't have permission to delete System");
        }
        engineRuleRepository.delete(rule);
        return "Rule deleted successfully!";
    }

    @Override
    public RuleResponseDto getRule(String title, Long idRule) {
        EngineRuleModel rule = engineRuleRepository.findById(idRule)
                .orElseThrow(() -> new IllegalArgumentException("Rule not found: " + title));

        return rule.toDto();
    }
}
