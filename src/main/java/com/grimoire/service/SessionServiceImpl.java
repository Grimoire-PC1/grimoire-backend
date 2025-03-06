package com.grimoire.service;

import com.grimoire.dto.session.SessionResponseDto;
import com.grimoire.dto.session.SessionTypeEnum;
import com.grimoire.service.service.SessionService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SessionServiceImpl implements SessionService {
    @Override
    public SessionResponseDto create(Long campaignId, SessionTypeEnum sessionTypeEnum, String name) {
        return null;
    }

    @Override
    public SessionResponseDto update(Long sessionId, SessionTypeEnum sessionTypeEnum, String name) {
        return null;
    }

    @Override
    public String delete(Long sessionId, String name) {
        return null;
    }

    @Override
    public Collection<SessionResponseDto> get(Long campaignId, String name) {
        return null;
    }
}
