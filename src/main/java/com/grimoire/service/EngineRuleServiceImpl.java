package com.grimoire.service;

import com.grimoire.dto.characterSheetTemplate.CharacterSheetTabResponseDto;
import com.grimoire.dto.engine.EngineTypeEnum;
import com.grimoire.dto.engineRule.RuleResponseDto;
import com.grimoire.dto.engineRule.RuleCreateRequestDto;
import com.grimoire.dto.engineRule.RulePostRequestDto;
import com.grimoire.model.grimoire.*;
import com.grimoire.repository.*;
import com.grimoire.service.interfaces.EngineRuleService;
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
    private final CampaignRepository campaignRepository;
    private final ParticipantRepository participantRepository;

    @Autowired
    public EngineRuleServiceImpl(EngineRuleRepository engineRuleRepository, EngineRepository engineRepository, UserRepository userRepository, CampaignRepository campaignRepository, ParticipantRepository participantRepository) {
        this.engineRuleRepository = engineRuleRepository;
        this.engineRepository = engineRepository;
        this.userRepository = userRepository;
        this.campaignRepository = campaignRepository;
        this.participantRepository = participantRepository;
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

        rule.setTitle(ruleDto.getTitle() == null ? rule.getTitle() : ruleDto.getTitle());
        rule.setDescription(ruleDto.getDescription() == null ? rule.getDescription() : ruleDto.getDescription());

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
        EngineModel engine = engineRepository.findById(idEngine)
                .orElseThrow(() -> new IllegalArgumentException("System not found: " + idEngine));
        if (!( engine.getOwner().equals(user) ||
                EngineTypeEnum.fromId(engine.getEngineType().getId()).equals(EngineTypeEnum.PUBLICO)
        )) {
            throw new AccessDeniedException("You don't have permission to this System");
        }
        Collection<EngineRuleModel> rules = engineRuleRepository.findAllFiltered(idRule, idEngine);

        return rules.stream().map(EngineRuleModel::toDto).toList();
    }

    @Override
    public Collection<RuleResponseDto> getByCampaign(Long campaignId, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Authorization error"));
        CampaignModel campaignModel = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new IllegalArgumentException("Campaign not found: " + campaignId));
        if (!campaignModel.getOwner().equals(user) && !participantRepository.exists(user.getId(), campaignModel.getId())) {
            throw new IllegalArgumentException("You are not part of this Campaign");
        }

        Collection<EngineRuleModel> models = engineRuleRepository.
                findByCampaign(campaignModel.getEngine().getId());

        return models.stream().map(EngineRuleModel::toDto).toList();
    }
}
