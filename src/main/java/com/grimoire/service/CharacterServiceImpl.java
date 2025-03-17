package com.grimoire.service;

import com.grimoire.dto.character.CharacterCreateRequestDto;
import com.grimoire.dto.character.CharacterPostRequestDto;
import com.grimoire.dto.character.CharacterResponseDto;
import com.grimoire.model.grimoire.CampaignModel;
import com.grimoire.model.grimoire.CharacterModel;
import com.grimoire.model.grimoire.EngineRuleModel;
import com.grimoire.model.grimoire.UserModel;
import com.grimoire.repository.CampaignRepository;
import com.grimoire.repository.CharacterRepository;
import com.grimoire.repository.ParticipantRepository;
import com.grimoire.repository.UserRepository;
import com.grimoire.service.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CharacterServiceImpl implements CharacterService {
    private final CharacterRepository characterRepository;
    private final CampaignRepository campaignRepository;
    private final UserRepository userRepository;
    private final ParticipantRepository participantRepository;

    @Autowired
    public CharacterServiceImpl(CharacterRepository characterRepository, CampaignRepository campaignRepository, UserRepository userRepository, ParticipantRepository participantRepository) {
        this.characterRepository = characterRepository;
        this.campaignRepository = campaignRepository;
        this.userRepository = userRepository;
        this.participantRepository = participantRepository;
    }

    @Override
    public CharacterResponseDto create(Long campaignId, CharacterCreateRequestDto characterDto, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));

        CampaignModel campaignModel = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new IllegalArgumentException("Campaign not found: " + campaignId));

        if (!campaignModel.getOwner().equals(user) && !participantRepository.exists(user.getId(), campaignModel.getId())) {
            throw new IllegalArgumentException("You are not part of this Campaign");
        }

        CharacterModel character = CharacterModel.builder()
                .user(user)
                .campaign(campaignModel)
                .name(characterDto.getName())
                .idPicture(characterDto.getIdPicture())
                .build();

        return characterRepository.save(character).toDto();
    }

    @Override
    public CharacterResponseDto edit(Long characterId, CharacterPostRequestDto characterDto, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));

        CharacterModel character = characterRepository.findById(characterId)
               .orElseThrow(() -> new IllegalArgumentException("Character not found: " + characterId));

        if (!character.getUser().equals(user) && !character.getCampaign().getOwner().equals(user)) {
            throw new IllegalArgumentException("You don't have permission to this Character");
        }

        character.setName(characterDto.getName().isBlank() ? character.getName() : characterDto.getName());
        character.setIdPicture(characterDto.getIdPicture() == null ? character.getIdPicture() : characterDto.getIdPicture());

        return characterRepository.save(character).toDto();
    }

    @Override
    public String delete(Long characterId, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));

        CharacterModel character = characterRepository.findById(characterId)
                .orElseThrow(() -> new IllegalArgumentException("Character not found: " + characterId));

        if (!character.getUser().equals(user) && !character.getCampaign().getOwner().equals(user)) {
            throw new IllegalArgumentException("You don't have permission to this Character");
        }

        characterRepository.delete(character);
        return "Character deleted successfully!";
    }

    @Override
    public Collection<CharacterResponseDto> getByUser(String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Authorization error"));
        Collection<CharacterModel> characters = characterRepository.findAllFiltered(user.getId(), null);

        return characters.stream().map(CharacterModel::toDto).toList();
    }

    @Override
    public Collection<CharacterResponseDto> getByCampaign(Long campaignId, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));

        CampaignModel campaignModel = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new IllegalArgumentException("Campaign not found: " + campaignId));

        if (!campaignModel.getOwner().equals(user) && !participantRepository.exists(user.getId(), campaignModel.getId())) {
            throw new IllegalArgumentException("You are not part of this Campaign");
        }

        Collection<CharacterModel> characters = characterRepository.findAllFiltered(null, campaignId);

        return characters.stream().map(CharacterModel::toDto).toList();
    }

    @Override
    public Collection<CharacterResponseDto> getOthersByCampaign(Long campaignId, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));

        CampaignModel campaignModel = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new IllegalArgumentException("Campaign not found: " + campaignId));

        if (!campaignModel.getOwner().equals(user) && !participantRepository.exists(user.getId(), campaignModel.getId())) {
            throw new IllegalArgumentException("You are not part of this Campaign");
        }

        Collection<CharacterModel> characters = characterRepository.findOthersFiltered(user.getId(), campaignId);

        return characters.stream().map(CharacterModel::toDto).toList();
    }
}
