package com.grimoire.service.service;

import com.grimoire.dto.engine.EngineCreateRequestDto;
import com.grimoire.dto.engine.EngineEditRequestDto;
import com.grimoire.dto.engine.EngineResponseDto;

public interface EngineService {
    String createEngine(String idUser, EngineCreateRequestDto engineDTO);
    String editEngine(Long idSys, Long idUser, EngineEditRequestDto request);
    String deleteEngine(Long idSys, Long idUser);
    EngineResponseDto getEngine(Long idSys);

}

