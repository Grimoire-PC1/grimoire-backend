package com.grimoire.service;

import com.grimoire.dto.characterSheetContent.CharacterSheetContentCreateRequestDto;
import com.grimoire.dto.characterSheetContent.CharacterSheetContentPostRequestDto;
import com.grimoire.dto.characterSheetContent.CharacterSheetContentResponseDto;
import com.grimoire.dto.characterSheetTemplate.CharacterSheetSubTabTypeEnum;
import com.grimoire.model.grimoire.*;
import com.grimoire.repository.*;
import com.grimoire.service.interfaces.CharacterSheetContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CharacterSheetContentServiceImpl implements CharacterSheetContentService {

    private final CharacterSheetContentRepository characterSheetContentRepository;
    private final CharacterRepository characterRepository;
    private final CampaignRepository campaignRepository;
    private final UserRepository userRepository;
    private final ParticipantRepository participantRepository;
    private final CharacterSheetSubTabRepository characterSheetSubTabRepository;

    @Autowired
    public CharacterSheetContentServiceImpl(CharacterSheetContentRepository characterSheetContentRepository, CharacterRepository characterRepository, CampaignRepository campaignRepository, UserRepository userRepository, ParticipantRepository participantRepository, CharacterSheetSubTabRepository characterSheetSubTabRepository) {
        this.characterSheetContentRepository = characterSheetContentRepository;
        this.characterRepository = characterRepository;
        this.campaignRepository = campaignRepository;
        this.userRepository = userRepository;
        this.participantRepository = participantRepository;
        this.characterSheetSubTabRepository = characterSheetSubTabRepository;
    }

    @Override
    public CharacterSheetContentResponseDto create(Long characterId, Long characterSheetSubTabId, CharacterSheetContentCreateRequestDto characterSheetDto, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
        CharacterModel character = characterRepository.findById(characterId)
                .orElseThrow(() -> new IllegalArgumentException("Character not found: " + characterId));
        if (!character.getUser().equals(user) && !character.getCampaign().getOwner().equals(user)) {
            throw new AccessDeniedException("You don't have permission to this Character");
        }

        CharacterSheetSubTabModel subTab = characterSheetSubTabRepository.findById(characterSheetSubTabId)
                .orElseThrow(() -> new IllegalArgumentException("Character Sheet Sub Tab not found: " + characterSheetSubTabId));
        if (!subTab.getCharacterSheetTabModel().getEngine().equals(character.getCampaign().getEngine())) {
            throw new IllegalArgumentException("Sheet Sub Tab not in Campaign Engine");
        }

        CharacterSheetContentModel model = CharacterSheetContentModel.builder()
                .characterModel(character)
                .subTabModel(subTab)
                .build();

        if (characterSheetDto.getContent() != null) {
            switch (CharacterSheetSubTabTypeEnum.fromId(subTab.getSubTabTypeModel().getId())) {
                case CharacterSheetSubTabTypeEnum.TEXTO:
                    model.setText(characterSheetDto.getContent().getFirst());
                    break;
                case CharacterSheetSubTabTypeEnum.INTEIRO:
                    model.setNumber(Integer.valueOf(characterSheetDto.getContent().getFirst()));
                    break;
                case CharacterSheetSubTabTypeEnum.DADO:
                    model.setDiceQuantity(Integer.valueOf(characterSheetDto.getContent().get(0)));
                    model.setDiceType(Integer.valueOf(characterSheetDto.getContent().get(1)));
                    model.setDiceBonus(Integer.valueOf(characterSheetDto.getContent().get(2)));
                    break;
            }
        }

        return characterSheetContentRepository.save(model).toDto();
    }

    @Override
    public CharacterSheetContentResponseDto edit(Long characterSheetContentId, CharacterSheetContentPostRequestDto characterSheetDto, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
        CharacterSheetContentModel model = characterSheetContentRepository.findById(characterSheetContentId)
                .orElseThrow(() -> new IllegalArgumentException("Character Sheet Content not found: " + characterSheetContentId));
        if (!model.getCharacterModel().getUser().equals(user) && !model.getCharacterModel().getCampaign().getOwner().equals(user)) {
            throw new AccessDeniedException("You don't have permission to this Character Sheet Content");
        }

        if (characterSheetDto.getContent() != null) {
            switch (CharacterSheetSubTabTypeEnum.fromId(model.getSubTabModel().getSubTabTypeModel().getId())) {
                case CharacterSheetSubTabTypeEnum.TEXTO:
                    model.setText(characterSheetDto.getContent().getFirst());
                    break;
                case CharacterSheetSubTabTypeEnum.INTEIRO:
                    model.setNumber(Integer.valueOf(characterSheetDto.getContent().getFirst()));
                    break;
                case CharacterSheetSubTabTypeEnum.DADO:
                    model.setDiceQuantity(Integer.valueOf(characterSheetDto.getContent().get(0)));
                    model.setDiceType(Integer.valueOf(characterSheetDto.getContent().get(1)));
                    model.setDiceBonus(Integer.valueOf(characterSheetDto.getContent().get(2)));
                    break;
            }
        }

        return characterSheetContentRepository.save(model).toDto();
    }

    @Override
    public String delete(Long characterSheetContentId, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
        CharacterSheetContentModel model = characterSheetContentRepository.findById(characterSheetContentId)
                .orElseThrow(() -> new IllegalArgumentException("Character Sheet Content not found: " + characterSheetContentId));
        if (!model.getCharacterModel().getUser().equals(user) && !model.getCharacterModel().getCampaign().getOwner().equals(user)) {
            throw new AccessDeniedException("You don't have permission to this Character Sheet Content");
        }
        characterSheetContentRepository.delete(model);
        return "Character Sheet Content deleted successfully!";
    }

    @Override
    public Collection<CharacterSheetContentResponseDto> get(Long characterId, Long characterSheetTabId, Long characterSheetSubTabId, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
        CharacterModel character = characterRepository.findById(characterId)
                .orElseThrow(() -> new IllegalArgumentException("Character not found: " + characterId));
        if (!character.getUser().equals(user) && !character.getCampaign().getOwner().equals(user)) {
            throw new AccessDeniedException("You don't have permission to this Character");
        }

        Collection<CharacterSheetContentModel> models = characterSheetContentRepository.findAllFiltered(
                characterId, characterSheetTabId, characterSheetSubTabId);

        return models.stream().map(CharacterSheetContentModel::toDto).toList();
    }
}
