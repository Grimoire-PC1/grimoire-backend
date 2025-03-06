package com.grimoire.service.service;

import com.grimoire.dto.session.SessionResponseDto;
import com.grimoire.dto.session.SessionTypeEnum;

import java.util.Collection;

public interface SessionService {
    SessionResponseDto create(Long campaignId, SessionTypeEnum sessionTypeEnum, String name);

    SessionResponseDto update(Long sessionId, SessionTypeEnum sessionTypeEnum, String name);

    String delete(Long sessionId, String name);

    Collection<SessionResponseDto> get(Long campaignId, String name);
}
