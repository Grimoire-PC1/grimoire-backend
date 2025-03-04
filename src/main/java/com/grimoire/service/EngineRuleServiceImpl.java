package com.grimoire.service;

import com.grimoire.dto.engineRule.RuleResponseDto;
import com.grimoire.dto.engineRule.RuleCreateRequestDto;
import com.grimoire.dto.engineRule.RulePostRequestDto;
import com.grimoire.model.grimoire.EngineModel;
import com.grimoire.model.grimoire.UserModel;
import com.grimoire.model.grimoire.EngineRuleModel;
import com.grimoire.repository.EngineRepository;
import com.grimoire.repository.EngineRuleRepository;
import com.grimoire.repository.UserRepository;
import com.grimoire.service.service.EngineRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class EngineRuleServiceImpl implements EngineRuleService {

    private final EngineRuleRepository engineRuleRepository;
    private final EngineRepository engineRepository;
    private final UserRepository userRepository;

    @Autowired
    public EngineRuleServiceImpl(EngineRuleRepository engineRuleRepository, EngineRepository engineRepository, UserRepository userRepository) {
        this.engineRuleRepository = engineRuleRepository;
        this.engineRepository = engineRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public RuleResponseDto createRule(RuleCreateRequestDto ruleDTO, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Authorization error"));
        EngineModel engine = engineRepository.findById(ruleDTO.getIdSys())
                .orElseThrow(() -> new IllegalArgumentException("System not found: " + ruleDTO.getIdSys()));
        if (!engine.getOwner().equals(user)) {
            throw new AccessDeniedException("You don't have permission to this System");
        }
        EngineRuleModel rule = EngineRuleModel.builder()
                .engine(engine)
                .title(ruleDTO.getTitle())
                .description(ruleDTO.getDescription())
                .build();

        return engineRuleRepository.save(rule).toDto();
    }

    @Override
    @Transactional
    public RuleResponseDto editRule(Long ruleId, RulePostRequestDto ruleDto, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Authorization error"));
        EngineRuleModel rule = engineRuleRepository.findById(ruleId)
                .orElseThrow(() -> new IllegalArgumentException("Rule not found: " + ruleId));
        if (!rule.getEngine().getOwner().equals(user)) {
            throw new AccessDeniedException("You don't have permission to this Rule");
        }

        rule.setTitle(ruleDto.getTitle().isBlank() ? rule.getTitle() : ruleDto.getTitle());
        rule.setDescription(ruleDto.getDescription().isBlank() ? rule.getDescription() : ruleDto.getDescription());

        return engineRuleRepository.save(rule).toDto();
    }

    @Override
    @Transactional
    public String deleteRule(Long ruleId, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Authorization error"));
        EngineRuleModel rule = engineRuleRepository.findById(ruleId)
                .orElseThrow(() -> new IllegalArgumentException("Rule not found: " + ruleId));
        if (!rule.getEngine().getOwner().equals(user)) {
            throw new AccessDeniedException("You don't have permission to this Rule");
        }
        engineRuleRepository.delete(rule);

        return "Rule deleted successfully!";
    }

    @Override
    public Collection<RuleResponseDto> getRules(Long idRule, Long idEngine, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Authorization error"));
        Collection<EngineRuleModel> rules = engineRuleRepository.findAllFiltered(idRule, idEngine, user.getId());

        return rules.stream().map(EngineRuleModel::toDto).toList();
    }
}
