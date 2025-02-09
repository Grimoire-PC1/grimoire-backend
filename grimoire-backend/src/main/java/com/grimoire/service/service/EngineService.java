package com.grimoire.service.service;

import com.grimoire.dto.engine.EngineCreateRequestDto;
import com.grimoire.dto.engine.EngineEditRequestDto;
import com.grimoire.dto.engine.EngineResponseDto;

public interface EngineService {
    String createEngine(EngineCreateRequestDto engineDTO);
    String editEngine(String name, EngineEditRequestDto request);
    String deleteEngine(String name);
    EngineResponseDto getEngine(String name);

}
