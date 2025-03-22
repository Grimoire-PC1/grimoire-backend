package com.grimoire.service.interfaces;

import com.grimoire.dto.characterSheetContent.CharacterSheetContentCreateRequestDto;
import com.grimoire.dto.characterSheetContent.CharacterSheetContentPostRequestDto;
import com.grimoire.dto.characterSheetContent.CharacterSheetContentResponseDto;

import java.util.Collection;

public interface CharacterSheetContentService {
    CharacterSheetContentResponseDto create(Long characterId, Long characterSheetSubTabId, CharacterSheetContentCreateRequestDto characterSheetDto, String username);
    CharacterSheetContentResponseDto edit(Long characterSheetContentId, CharacterSheetContentPostRequestDto characterSheetDto, String username);
    String delete(Long characterSheetContentId, String username);
    Collection<CharacterSheetContentResponseDto> get(Long characterId, Long characterSheetTabId, Long characterSheetSubTabId, String username);
}
