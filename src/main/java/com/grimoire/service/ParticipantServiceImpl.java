package com.grimoire.service;

import com.grimoire.dto.participant.ParticipantResponseDto;
import com.grimoire.model.grimoire.CampaignModel;
import com.grimoire.model.grimoire.ParticipantModel;
import com.grimoire.model.grimoire.UserModel;
import com.grimoire.model.grimoire.embeddedId.ParticipantModelId;
import com.grimoire.repository.CampaignRepository;
import com.grimoire.repository.ParticipantRepository;
import com.grimoire.repository.UserRepository;
import com.grimoire.service.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class ParticipantServiceImpl implements ParticipantService {
    private final UserRepository userRepository;
    private final CampaignRepository campaignRepository;
    private final ParticipantRepository participantRepository;

    @Autowired
    public ParticipantServiceImpl(UserRepository userRepository, CampaignRepository campaignRepository, ParticipantRepository participantRepository) {
        this.userRepository = userRepository;
        this.campaignRepository = campaignRepository;
        this.participantRepository = participantRepository;
    }


    @Override
    public ParticipantResponseDto create(Long campaignId, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Authorization error"));

        CampaignModel campaignModel = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new IllegalArgumentException("Campaign not found: " + campaignId));

        if (campaignModel.getOwner().equals(user)) {
            throw new IllegalArgumentException("You are the Master of this Campaign");
        }

        ParticipantModel model = ParticipantModel.builder()
                .modelId(new ParticipantModelId(user, campaignModel))
                .build();

        return participantRepository.save(model).toDto();
    }

    @Override
    public String delete(Long campaignId, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Authorization error"));

        CampaignModel campaignModel = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new IllegalArgumentException("Campaign not found: " + campaignId));

        ParticipantModel model = participantRepository.findById(new ParticipantModelId(user, campaignModel))
                .orElseThrow(() -> new IllegalArgumentException("Participant not found"));

        participantRepository.removeParticipantData(
                model.getModelId().getUserModel().getId(),
                model.getModelId().getCampaignModel().getId());
        return "Participant deleted successfully!";
    }

    @Override
    public Collection<ParticipantResponseDto> get(Long campaignId, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Authorization error"));
        CampaignModel campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new IllegalArgumentException("Campaign not found: " + campaignId));

        if (!campaign.getOwner().equals(user) || participantRepository.existsById(new ParticipantModelId(user, campaign))) {
            throw new AccessDeniedException("You don't have permission to this Campaign");
        }

        List<ParticipantModel> participantModels = participantRepository.findAllByCampaign(campaignId);

        return participantModels.stream().map(ParticipantModel::toDto).toList();
    }
}
