package com.grimoire.service.service;

import com.grimoire.dto.characterSheet.*;

import java.util.Collection;

public interface CharacterSheetService {
    CharacterSheetTabResponseDto createTab(Long engineId, CharacterSheetTabCreateRequestDto characterSheetTabDto, String name);

    CharacterSheetTabResponseDto editTab(Long characterSheetTabId, CharacterSheetTabPostRequestDto characterSheetTabDto, String name);

    String deleteTab(Long characterSheetTabId, String name);

    Collection<CharacterSheetTabResponseDto> getTab(Long engineId, String name);

    CharacterSheetSubTabResponseDto createSubTab(Long characterSheetTabId, CharacterSheetSubTabTypeEnum characterSheetSubTabTypeEnum, CharacterSheetSubTabCreateRequestDto characterSheetSubTabDto, String name);

    CharacterSheetSubTabResponseDto editSubTab(Long characterSheetSubTabId, CharacterSheetSubTabPostRequestDto characterSheetSubTabDto, String name);

    String deleteSubTab(Long characterSheetSubTabId, String name);

    Collection<CharacterSheetSubTabResponseDto> getSubTab(Long engineId, Long characterSheetTabId, String name);
}
