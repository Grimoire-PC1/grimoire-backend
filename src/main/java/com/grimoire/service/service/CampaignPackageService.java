package com.grimoire.service.service;

import com.grimoire.dto.campaignPackage.*;

import java.util.Collection;

public interface CampaignPackageService {
    CampaignPackageResponseDto create(Long campaignId, Boolean isPublic, CampaignPackageCreateRequestDto campaignPackageDto, String name);

    CampaignPackageResponseDto createByCharacter(Long characterId, CampaignPackageCreateRequestDto campaignPackageDto, String name);

    CampaignPackageResponseDto post(Long packageId, CampaignPackagePostRequestDto campaignPackageDto, String name);

    String delete(Long packageId, String name);

    Collection<CampaignPackageResponseDto> get(Long campaignId, Long parentPackageId, String name);

    CampaignFileResponseDto createFile(Long packageId, CampaignFileTypeEnum campaignFileTypeEnum, CampaignFileCreateRequestDto campaignFileDto, String name);

    CampaignFileResponseDto updateFile(Long campaignFileId, CampaignFilePostRequestDto campaignFileDto, String name);

    String deleteFile(Long campaignFileId, String name);

    Collection<CampaignFileResponseDto> getFile(Long campaignId, Long parentPackageId, String name);
}
