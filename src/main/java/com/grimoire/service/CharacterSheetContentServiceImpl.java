package com.grimoire.service;

import com.grimoire.dto.characterSheetContent.CharacterSheetContentCreateRequestDto;
import com.grimoire.dto.characterSheetContent.CharacterSheetContentPostRequestDto;
import com.grimoire.dto.characterSheetContent.CharacterSheetContentResponseDto;
import com.grimoire.repository.CharacterSheetContentService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CharacterSheetContentServiceImpl implements CharacterSheetContentService {


    @Override
    public CharacterSheetContentResponseDto create(Long characterId, Long characterSheetSubTabId, CharacterSheetContentCreateRequestDto characterSheetDto, String username) {
        return null;
    }

    @Override
    public CharacterSheetContentResponseDto edit(Long characterSheetContentId, CharacterSheetContentPostRequestDto characterSheetDto, String username) {
        return null;
    }

    @Override
    public String delete(Long characterSheetContentId, String username) {
        return null;
    }

    @Override
    public Collection<CharacterSheetContentResponseDto> get(Long characterId, Long characterSheetTabId, Long characterSheetSubTabId, String username) {
        return null;
    }
}
