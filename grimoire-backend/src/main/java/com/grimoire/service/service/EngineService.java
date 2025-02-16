package com.grimoire.service.service;

import com.grimoire.dto.engine.EngineCreateRequestDto;
import com.grimoire.dto.engine.EngineEditRequestDto;
import com.grimoire.dto.engine.EngineResponseDto;
import com.grimoire.dto.engine.EngineTypeEnum;

import java.util.Collection;

public interface EngineService {
    String createEngine(String idUser, EngineCreateRequestDto engineDTO, EngineTypeEnum engineTypeEnum);
    String editEngine(Long idSys, String username, EngineEditRequestDto request, EngineTypeEnum engineTypeEnum);
    String deleteEngine(Long idSys, String username);
    Collection<EngineResponseDto> getUserEngines(Long idSys, String username);
    Collection<EngineResponseDto> getPublicEngines();
}

