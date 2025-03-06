package com.grimoire.service.service;

import com.grimoire.dto.session.SessionCreateRequestDto;
import com.grimoire.dto.session.SessionPostRequestDto;
import com.grimoire.dto.session.SessionResponseDto;
import com.grimoire.dto.session.SessionTypeEnum;

import java.util.Collection;

public interface SessionService {
    SessionResponseDto create(Long campaignId, SessionTypeEnum sessionTypeEnum, SessionCreateRequestDto sessionDto, String name);

    SessionResponseDto update(Long sessionId, SessionTypeEnum sessionTypeEnum, SessionPostRequestDto sessionDto, String name);

    String delete(Long sessionId, String name);

    Collection<SessionResponseDto> getByCampaign(Long campaignId, String name);
}
