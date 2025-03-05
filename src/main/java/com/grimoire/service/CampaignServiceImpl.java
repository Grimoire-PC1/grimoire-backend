package com.grimoire.service;

import com.grimoire.dto.campaign.CampaignCreateRequestDto;
import com.grimoire.dto.campaign.CampaignPostRequestDto;
import com.grimoire.dto.campaign.CampaignResponseDto;
import com.grimoire.model.grimoire.CampaignModel;
import com.grimoire.model.grimoire.EngineModel;
import com.grimoire.model.grimoire.UserModel;
import com.grimoire.repository.CampaignRepository;
import com.grimoire.repository.EngineRepository;
import com.grimoire.repository.UserRepository;
import com.grimoire.service.service.CampaignService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CampaignServiceImpl implements CampaignService {

    private final CampaignRepository campaignRepository;
    private final EngineRepository engineRepository;
    private final UserRepository userRepository;

    @Autowired
    public CampaignServiceImpl(CampaignRepository campaignRepository, EngineRepository engineRepository, UserRepository userRepository) {
        this.campaignRepository = campaignRepository;
        this.engineRepository = engineRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public CampaignResponseDto createCampaign(CampaignCreateRequestDto campaignDto, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Authorization error"));
        EngineModel engine = null;
        if (!(campaignDto.getIdSystem() == null)) {
            engine = engineRepository.findById(campaignDto.getIdSystem())
                    .orElseThrow(() -> new IllegalArgumentException("System not found: " + campaignDto.getIdSystem()));
            if (!engine.getOwner().equals(user)) {
                throw new AccessDeniedException("You don't have permission to this System");
            }
        }

        CampaignModel campaign = CampaignModel.builder()
                .owner(user)
                .title(campaignDto.getTitle())
                .description(campaignDto.getDescription())
                .engine(engine)
                .idPicture(campaignDto.getIdPicture())
                .build();

        return campaignRepository.save(campaign).toDto();
    }

    @Override
    @Transactional
    public CampaignResponseDto postCampaign(Long idCampaign, CampaignPostRequestDto campaignDto, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Authorization error"));
        CampaignModel campaign = campaignRepository.findById(idCampaign)
                .orElseThrow(() -> new IllegalArgumentException("Campaign not found: " + idCampaign));
        if (!campaign.getOwner().equals(user)) {
            throw new AccessDeniedException("You don't have permission to this System");
        }
        campaign.setTitle(campaignDto.getTitle() == null ? campaign.getTitle() : campaignDto.getTitle());
        campaign.setDescription(campaignDto.getDescription() == null ? campaign.getDescription() : campaignDto.getDescription());
        campaign.setIdPicture(campaignDto.getIdPicture() == null ? campaign.getIdPicture() : campaignDto.getIdPicture());
        if (!(campaignDto.getIdSystem() == null)) {
            EngineModel engine = engineRepository.findById(campaignDto.getIdSystem())
                    .orElseThrow(() -> new IllegalArgumentException("System not found: " + campaignDto.getIdSystem()));
            if (!engine.getOwner().equals(user)) {
                throw new AccessDeniedException("You don't have permission to this Campaign");
            }
            campaign.setEngine(engine);
        }

        return campaignRepository.save(campaign).toDto();
    }

    @Override
    @Transactional
    public String deleteCampaign(Long idCampaign, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Authorization error"));
        CampaignModel campaign = campaignRepository.findById(idCampaign)
                .orElseThrow(() -> new IllegalArgumentException("Campaign not found: " + idCampaign));
        if (!campaign.getOwner().equals(user)) {
            throw new AccessDeniedException("You don't have permission to this Campaign");
        }

        campaignRepository.delete(campaign);
        return "Campaign deleted successfully!";
    }

    @Override
    public Collection<CampaignResponseDto> getCampaign(Long idCampaign, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
        Collection<CampaignModel> campaigns = campaignRepository.findAllFiltered(idCampaign, user.getId());

        return campaigns.stream().map(CampaignModel::toDto).toList();
    }

    @Override
    public Collection<CampaignResponseDto> getParticipatingCampaigns(String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
        Collection<CampaignModel> campaigns = campaignRepository.findAllParticipating(user.getId());

        return campaigns.stream().map(CampaignModel::toDto).toList();
    }
}
