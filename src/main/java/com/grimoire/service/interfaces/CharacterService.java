package com.grimoire.service.interfaces;

import com.grimoire.dto.character.CharacterCreateRequestDto;
import com.grimoire.dto.character.CharacterPostRequestDto;
import com.grimoire.dto.character.CharacterResponseDto;

import java.util.Collection;

public interface CharacterService {
    CharacterResponseDto create(Long campaignId, CharacterCreateRequestDto characterDto, String name);

    CharacterResponseDto edit(Long characterId, CharacterPostRequestDto characterDto, String name);

    String delete(Long characterId, String name);

    Collection<CharacterResponseDto> getByUser(String name);

    Collection<CharacterResponseDto> getByCampaign(Long campaignId, String name);

    Collection<CharacterResponseDto> getOthersByCampaign(Long campaignId, String name);
}
