package com.grimoire.service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.grimoire.dto.mechanic.MechanicCreateRequestDto;
import com.grimoire.dto.mechanic.MechanicPostRequestDto;
import com.grimoire.dto.mechanic.MechanicResponseDto;

import java.util.Collection;

public interface MechanicService {
    MechanicResponseDto create(Long engineId, MechanicCreateRequestDto mechanicDto, String name) throws JsonProcessingException;

    MechanicResponseDto edit(Long mechanicId, MechanicPostRequestDto mechanicDto, String name) throws JsonProcessingException;

    String delete(Long mechanicId, String name);

    Collection<MechanicResponseDto> get(Long engineId, String name);
}
