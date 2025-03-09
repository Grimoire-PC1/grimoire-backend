package com.grimoire.service;

import com.grimoire.dto.characterSheet.*;
import com.grimoire.service.service.CharacterSheetService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CharacterSheetServiceImpl implements CharacterSheetService {
    @Override
    public CharacterSheetTabResponseDto createTab(Long engineId, CharacterSheetTabCreateRequestDto characterSheetTabDto, String name) {
        return null;
    }

    @Override
    public CharacterSheetTabResponseDto editTab(Long characterSheetTabId, CharacterSheetTabPostRequestDto characterSheetTabDto, String name) {
        return null;
    }

    @Override
    public String deleteTab(Long characterSheetTabId, String name) {
        return null;
    }

    @Override
    public Collection<CharacterSheetTabResponseDto> getTab(Long engineId, String name) {
        return null;
    }

    @Override
    public CharacterSheetSubTabResponseDto createSubTab(Long characterSheetTabId, CharacterSheetSubTabTypeEnum characterSheetSubTabTypeEnum, CharacterSheetSubTabCreateRequestDto characterSheetSubTabDto, String name) {
        return null;
    }

    @Override
    public CharacterSheetSubTabResponseDto editSubTab(Long characterSheetSubTabId, CharacterSheetSubTabPostRequestDto characterSheetSubTabDto, String name) {
        return null;
    }

    @Override
    public String deleteSubTab(Long characterSheetSubTabId, String name) {
        return null;
    }

    @Override
    public Collection<CharacterSheetSubTabResponseDto> getSubTab(Long engineId, Long characterSheetTabId, String name) {
        return null;
    }
}
