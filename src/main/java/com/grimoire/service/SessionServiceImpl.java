package com.grimoire.service;

import com.grimoire.dto.session.SessionCreateRequestDto;
import com.grimoire.dto.session.SessionPostRequestDto;
import com.grimoire.dto.session.SessionResponseDto;
import com.grimoire.dto.session.SessionTypeEnum;
import com.grimoire.model.grimoire.CampaignModel;
import com.grimoire.model.grimoire.SessionModel;
import com.grimoire.model.grimoire.UserModel;
import com.grimoire.model.grimoire.joinTables.SessionTypeModel;
import com.grimoire.repository.CampaignRepository;
import com.grimoire.repository.ParticipantRepository;
import com.grimoire.repository.SessionRepository;
import com.grimoire.repository.UserRepository;
import com.grimoire.service.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SessionServiceImpl implements SessionService {
    private final SessionRepository sessionRepository;
    private final CampaignRepository campaignRepository;
    private final ParticipantRepository participantRepository;
    private final UserRepository userRepository;

    @Autowired
    public SessionServiceImpl(SessionRepository sessionRepository, CampaignRepository campaignRepository, ParticipantRepository participantRepository, UserRepository userRepository) {
        this.sessionRepository = sessionRepository;
        this.campaignRepository = campaignRepository;
        this.participantRepository = participantRepository;
        this.userRepository = userRepository;
    }

    @Override
    public SessionResponseDto create(Long campaignId, SessionTypeEnum sessionTypeEnum, SessionCreateRequestDto sessionDto, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));

        CampaignModel campaignModel = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new IllegalArgumentException("Campaign not found: " + campaignId));

        if (!campaignModel.getOwner().equals(user)) {
            throw new IllegalArgumentException("You don't have permission to this Campaign");
        }

        SessionModel sessionModel = SessionModel.builder()
                .title(sessionDto.getTitle())
                .campaign(campaignModel)
                .date(sessionDto.getDate())
                .description(sessionDto.getDescription())
                .sessionType(new SessionTypeModel(sessionTypeEnum))
                .fixed(sessionDto.getFixed())
                .build();

        return sessionRepository.save(sessionModel).toDto();
    }

    @Override
    public SessionResponseDto update(Long sessionId, SessionTypeEnum sessionTypeEnum, SessionPostRequestDto sessionDto, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
        SessionModel sessionModel = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("Session not found: " + sessionId));

        if (!sessionModel.getCampaign().getOwner().equals(user)) {
            throw new IllegalArgumentException("You don't have permission to this Session");
        }

        sessionModel.setTitle(sessionDto.getTitle() == null ? sessionModel.getTitle() : sessionDto.getTitle());
        sessionModel.setDate(sessionDto.getDate() == null ? sessionModel.getDate() : sessionDto.getDate());
        sessionModel.setDescription(sessionDto.getDescription() == null ? sessionModel.getDescription() : sessionDto.getDescription());
        sessionModel.setFixed(sessionDto.getFixed() == null ? sessionModel.getFixed() : sessionDto.getFixed());
        sessionModel.setSessionType(sessionTypeEnum == null ? sessionModel.getSessionType() : new SessionTypeModel(sessionTypeEnum));

        return sessionRepository.save(sessionModel).toDto();
    }

    @Override
    public String delete(Long sessionId, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
        SessionModel sessionModel = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("Session not found: " + sessionId));

        if (!sessionModel.getCampaign().getOwner().equals(user)) {
            throw new IllegalArgumentException("You don't have permission to this Session");
        }

        sessionRepository.delete(sessionModel);
        return "Session deleted successfully!";
    }

    @Override
    public Collection<SessionResponseDto> getByCampaign(Long campaignId, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));

        CampaignModel campaignModel = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new IllegalArgumentException("Campaign not found: " + campaignId));

        if (!campaignModel.getOwner().equals(user) && !participantRepository.exists(user.getId(), campaignModel.getId())) {
            throw new IllegalArgumentException("You are not part of this Campaign");
        }

        Collection<SessionModel> sessions = sessionRepository.findAllByCampaign(campaignId);
        return sessions.stream().map(SessionModel::toDto).toList();
    }
}
