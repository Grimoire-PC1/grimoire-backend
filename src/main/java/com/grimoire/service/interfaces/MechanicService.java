package com.grimoire.service.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.grimoire.dto.mechanic.MechanicCreateRequestDto;
import com.grimoire.dto.mechanic.MechanicPostRequestDto;
import com.grimoire.dto.mechanic.MechanicResponseDto;

import java.util.Collection;

public interface MechanicService {
    MechanicResponseDto create(Long engineId, MechanicCreateRequestDto mechanicDto, String username) throws JsonProcessingException;

    MechanicResponseDto edit(Long mechanicId, MechanicPostRequestDto mechanicDto, String username) throws JsonProcessingException;

    String delete(Long mechanicId, String username);

    Collection<MechanicResponseDto> get(Long engineId, String username);

    Collection<MechanicResponseDto> getByCampaign(Long campaignId, String username);
}
