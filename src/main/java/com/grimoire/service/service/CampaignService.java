package com.grimoire.service.service;

import com.grimoire.dto.campaign.CampaignCreateRequestDto;
import com.grimoire.dto.campaign.CampaignPostRequestDto;
import com.grimoire.dto.campaign.CampaignResponseDto;

import java.util.Collection;

public interface CampaignService {

    CampaignResponseDto createCampaign(CampaignCreateRequestDto campaignDto, String username);

    CampaignResponseDto postCampaign(Long idCampaign, CampaignPostRequestDto campaignDto, String username);

    String deleteCampaign(Long idCampaign, String username);

    Collection<CampaignResponseDto> getCampaign(Long idCampaign, String username);

    Collection<CampaignResponseDto> getParticipatingCampaigns(String name);
}
