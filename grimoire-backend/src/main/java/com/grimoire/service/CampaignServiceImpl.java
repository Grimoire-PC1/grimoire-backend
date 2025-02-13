package com.grimoire.service;

import com.grimoire.dto.campaign.CampaignCreateRequestDto;
import com.grimoire.dto.campaign.CampaignPostRequestDto;
import com.grimoire.dto.campaign.CampaignResponseDto;
import com.grimoire.model.grimoire.CampaignModel;
import com.grimoire.repository.CampaignRepository;
import com.grimoire.service.service.CampaignService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CampaignServiceImpl implements CampaignService {

    private final CampaignRepository campaignRepository;

    @Autowired
    public CampaignServiceImpl(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }

    @Override
    @Transactional
    public String createCampaign(CampaignCreateRequestDto campaignDTO) {
        if (campaignRepository.existsByCampaignName(campaignDTO.getTitle())) {
            throw new RuntimeException("Campaign title already exists!");
        }
        CampaignModel campaign = CampaignModel.builder()
                .idMaster(campaignDTO.getIdMaster())
                .title(campaignDTO.getTitle())
                .description(campaignDTO.getDescription())
                .idSystem(campaignDTO.getIdSystem())
                .pictureUrl(campaignDTO.getPictureUrl())
                .build();

        campaignRepository.save(campaign);
        return "User registered successfully!";
    }

    @Override
    @Transactional
    public String postCampaign(String campaignName, CampaignPostRequestDto campaignDTO) {
        CampaignModel campaign = campaignRepository.findByCampaignName(campaignName)
                .orElseThrow(() -> new IllegalArgumentException("Campaign not found: " + campaignName));
        campaign.setIdMaster(campaignDTO.getIdMaster() == 0 ? campaign.getIdMaster() : campaignDTO.getIdMaster());
        campaign.setTitle(campaignDTO.getTitle().isBlank() ? campaign.getTitle() : campaignDTO.getTitle());
        campaign.setDescription(campaignDTO.getDescription().isBlank() ? campaign.getDescription() : campaignDTO.getDescription());
        campaign.setIdSystem(campaignDTO.getIdSystem() == 0 ? campaign.getIdSystem() : campaignDTO.getIdSystem());
        campaign.setPictureUrl(campaignDTO.getPictureUrl().isBlank() ? campaign.getPictureUrl() : campaignDTO.getPictureUrl());

        campaignRepository.save(campaign);
        return "Campaign updated successfully!";
    }

    @Override
    @Transactional
    public String deleteCampaign(String campaignName) {
        CampaignModel campaign = campaignRepository.findByCampaignName(campaignName)
                .orElseThrow(() -> new IllegalArgumentException("Campaign not found: " + campaignName));

        campaignRepository.delete(campaign);
        return "Campaign deleted successfully!";

    }

    @Override
    public CampaignResponseDto getCampaign(String campaignName) {
        CampaignModel campaign = campaignRepository.findByCampaignName(campaignName)
                .orElseThrow(() -> new IllegalArgumentException("Campaign not found: " + campaignName));

        return campaign.toDto();
    }
}
