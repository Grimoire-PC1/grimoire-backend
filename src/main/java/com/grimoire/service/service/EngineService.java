package com.grimoire.service.service;

import com.grimoire.dto.engine.EngineCreateRequestDto;
import com.grimoire.dto.engine.EnginePostRequestDto;
import com.grimoire.dto.engine.EngineResponseDto;
import com.grimoire.dto.engine.EngineTypeEnum;

import java.util.Collection;

public interface EngineService {
    EngineResponseDto createEngine(String idUser, EngineCreateRequestDto engineDTO, EngineTypeEnum engineTypeEnum);
    EngineResponseDto editEngine(Long idSys, String username, EnginePostRequestDto request, EngineTypeEnum engineTypeEnum);
    String deleteEngine(Long idSys, String username);
    Collection<EngineResponseDto> getUserEngines(Long idSys, String username);
    Collection<EngineResponseDto> getPublicEngines();
}

