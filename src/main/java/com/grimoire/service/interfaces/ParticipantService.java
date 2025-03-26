package com.grimoire.service.interfaces;

import com.grimoire.dto.participant.ParticipantResponseDto;

import java.util.Collection;

public interface ParticipantService {
    ParticipantResponseDto create(Long campaignId, String username);

    String delete(Long campaignId, String username);

    Collection<ParticipantResponseDto> get(Long campaignId, String username);
}
