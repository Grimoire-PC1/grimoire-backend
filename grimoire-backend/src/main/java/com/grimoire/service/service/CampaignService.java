package com.grimoire.service.service;

import com.grimoire.dto.campaign.CampaignCreateRequestDto;
import com.grimoire.dto.campaign.CampaignPostRequestDto;
import com.grimoire.dto.campaign.CampaignResponseDto;

public interface CampaignService {
    String createCampaign(CampaignCreateRequestDto campaignDTO);
    String postCampaign(String campaignName, CampaignPostRequestDto request);
    String deleteCampaign(String campaignName);
    CampaignResponseDto getCampaign(String campaignName);

}
