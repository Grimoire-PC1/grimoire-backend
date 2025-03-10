package com.grimoire.service;

import com.grimoire.dto.characterSheetTemplate.*;
import com.grimoire.model.grimoire.*;
import com.grimoire.model.grimoire.typeTables.CharacterSheetSubTabTypeModel;
import com.grimoire.repository.*;
import com.grimoire.service.service.CharacterSheetTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CharacterSheetTemplateServiceImpl implements CharacterSheetTemplateService {
    private final CharacterSheetTabRepository characterSheetTabRepository;
    private final CharacterSheetSubTabRepository characterSheetSubTabRepository;
    private final EngineRepository engineRepository;
    private final UserRepository userRepository;
    private final CampaignRepository campaignRepository;
    private final ParticipantRepository participantRepository;

    @Autowired
    public CharacterSheetTemplateServiceImpl(CharacterSheetTabRepository characterSheetTabRepository, CharacterSheetSubTabRepository characterSheetSubTabRepository, EngineRepository engineRepository, UserRepository userRepository, CampaignRepository campaignRepository, ParticipantRepository participantRepository) {
        this.characterSheetTabRepository = characterSheetTabRepository;
        this.characterSheetSubTabRepository = characterSheetSubTabRepository;
        this.engineRepository = engineRepository;
        this.userRepository = userRepository;
        this.campaignRepository = campaignRepository;
        this.participantRepository = participantRepository;
    }

    @Override
    public CharacterSheetTabResponseDto createTab(Long engineId, CharacterSheetTabCreateRequestDto characterSheetTabDto, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Authorization error"));
        EngineModel engine = engineRepository.findById(engineId)
                .orElseThrow(() -> new IllegalArgumentException("System not found: " + engineId));
        if (!engine.getOwner().equals(user)) {
            throw new AccessDeniedException("You don't have permission to this System");
        }
        CharacterSheetTabModel model = CharacterSheetTabModel.builder()
                .engine(engine)
                .name(characterSheetTabDto.getName())
                .build();

        return characterSheetTabRepository.save(model).toDto();
    }

    @Override
    public CharacterSheetTabResponseDto editTab(Long characterSheetTabId, CharacterSheetTabPostRequestDto characterSheetTabDto, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Authorization error"));

        CharacterSheetTabModel model = characterSheetTabRepository.findById(characterSheetTabId)
                .orElseThrow(() -> new IllegalArgumentException("Character Sheet Tab not found: " + characterSheetTabId));
        if (!model.getEngine().getOwner().equals(user)) {
            throw new AccessDeniedException("You don't have permission to this Character Sheet Tab");
        }
        model.setName(characterSheetTabDto.getName() == null ? model.getName() : characterSheetTabDto.getName());

        return characterSheetTabRepository.save(model).toDto();
    }

    @Override
    public String deleteTab(Long characterSheetTabId, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Authorization error"));

        CharacterSheetTabModel model = characterSheetTabRepository.findById(characterSheetTabId)
                .orElseThrow(() -> new IllegalArgumentException("Character Sheet Tab not found: " + characterSheetTabId));
        if (!model.getEngine().getOwner().equals(user)) {
            throw new AccessDeniedException("You don't have permission to this Character Sheet Tab");
        }

        characterSheetTabRepository.delete(model);
        return "Character Sheet Tab deleted successfully!";
    }

    @Override
    public Collection<CharacterSheetTabResponseDto> getTab(Long engineId, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Authorization error"));
        Collection<CharacterSheetTabModel> models = characterSheetTabRepository.findAllFiltered(engineId, user.getId());

        return models.stream().map(CharacterSheetTabModel::toDto).toList();
    }

    @Override
    public Collection<CharacterSheetTabResponseDto> getTabByCampaign(Long campaignId, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Authorization error"));
        CampaignModel campaignModel = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new IllegalArgumentException("Campaign not found: " + campaignId));
        if (!campaignModel.getOwner().equals(user) && !participantRepository.exists(user.getId(), campaignModel.getId())) {
            throw new IllegalArgumentException("You are not part of this Campaign");
        }

        Collection<CharacterSheetTabModel> models = characterSheetTabRepository.
                findAllFiltered(campaignModel.getEngine().getId(), user.getId());

        return models.stream().map(CharacterSheetTabModel::toDto).toList();
    }

    @Override
    public CharacterSheetSubTabResponseDto createSubTab(Long characterSheetTabId, CharacterSheetSubTabTypeEnum characterSheetSubTabTypeEnum, CharacterSheetSubTabCreateRequestDto characterSheetSubTabDto, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Authorization error"));

        CharacterSheetTabModel tabModel = characterSheetTabRepository.findById(characterSheetTabId)
                .orElseThrow(() -> new IllegalArgumentException("Character Sheet Tab not found: " + characterSheetTabId));
        if (!tabModel.getEngine().getOwner().equals(user)) {
            throw new AccessDeniedException("You don't have permission to this Character Sheet Tab");
        }

        CharacterSheetSubTabModel model = CharacterSheetSubTabModel.builder()
                .characterSheetTabModel(tabModel)
                .subTabTypeModel(new CharacterSheetSubTabTypeModel(characterSheetSubTabTypeEnum))
                .name(characterSheetSubTabDto.getName())
                .build();

        return characterSheetSubTabRepository.save(model).toDto();
    }

    @Override
    public CharacterSheetSubTabResponseDto editSubTab(Long characterSheetSubTabId, CharacterSheetSubTabPostRequestDto characterSheetSubTabDto, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Authorization error"));

        CharacterSheetSubTabModel model = characterSheetSubTabRepository.findById(characterSheetSubTabId)
                .orElseThrow(() -> new IllegalArgumentException("Character Sheet Sub Tab not found: " + characterSheetSubTabId));
        if (!model.getCharacterSheetTabModel().getEngine().getOwner().equals(user)) {
            throw new AccessDeniedException("You don't have permission to this Character Sheet Sub Tab");
        }

        model.setName(characterSheetSubTabDto.getName() == null ? model.getName() : characterSheetSubTabDto.getName());

        return characterSheetSubTabRepository.save(model).toDto();
    }

    @Override
    public String deleteSubTab(Long characterSheetSubTabId, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Authorization error"));

        CharacterSheetSubTabModel model = characterSheetSubTabRepository.findById(characterSheetSubTabId)
                .orElseThrow(() -> new IllegalArgumentException("Character Sheet Sub Tab not found: " + characterSheetSubTabId));
        if (!model.getCharacterSheetTabModel().getEngine().getOwner().equals(user)) {
            throw new AccessDeniedException("You don't have permission to this Character Sheet Sub Tab");
        }

        characterSheetSubTabRepository.delete(model);

        return "Character Sheet Sub Tab deleted successfully!";
    }

    @Override
    public Collection<CharacterSheetSubTabResponseDto> getSubTab(
            Long engineId, Long characterSheetTabId, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Authorization error"));
        Collection<CharacterSheetSubTabModel> models = characterSheetSubTabRepository.findAllFiltered(engineId, characterSheetTabId, user.getId());

        return models.stream().map(CharacterSheetSubTabModel::toDto).toList();
    }

    @Override
    public Collection<CharacterSheetSubTabResponseDto> getSubTabByCampaign(Long campaignId, Long characterSheetTabId, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Authorization error"));
        CampaignModel campaignModel = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new IllegalArgumentException("Campaign not found: " + campaignId));
        if (!campaignModel.getOwner().equals(user) && !participantRepository.exists(user.getId(), campaignModel.getId())) {
            throw new IllegalArgumentException("You are not part of this Campaign");
        }

        Collection<CharacterSheetSubTabModel> models = characterSheetSubTabRepository.findAllFiltered(
                campaignModel.getEngine().getId(), characterSheetTabId, user.getId());

        return models.stream().map(CharacterSheetSubTabModel::toDto).toList();
    }
}
