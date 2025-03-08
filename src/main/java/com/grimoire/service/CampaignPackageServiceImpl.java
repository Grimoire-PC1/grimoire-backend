package com.grimoire.service;

import com.grimoire.dto.campaignPackage.*;
import com.grimoire.model.grimoire.CampaignModel;
import com.grimoire.model.grimoire.UserModel;
import com.grimoire.repository.*;
import com.grimoire.service.service.CampaignPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CampaignPackageServiceImpl implements CampaignPackageService {
    private final CampaignPackageRepository campaignPackageRepository;
    private final CampaignFileRepository campaignFileRepository;
    private final CampaignRepository campaignRepository;
    private final ParticipantRepository participantRepository;
    private final UserRepository userRepository;

    @Autowired
    public CampaignPackageServiceImpl(CampaignPackageRepository campaignPackageRepository, CampaignFileRepository campaignFileRepository, CampaignRepository campaignRepository, ParticipantRepository participantRepository, UserRepository userRepository) {
        this.campaignPackageRepository = campaignPackageRepository;
        this.campaignFileRepository = campaignFileRepository;
        this.campaignRepository = campaignRepository;
        this.participantRepository = participantRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CampaignPackageResponseDto create(Long campaignId, Boolean isPublic, CampaignPackageCreateRequestDto campaignPackageDto, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));

        CampaignModel campaignModel = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new IllegalArgumentException("Campaign not found: " + campaignId));

        if (!campaignModel.getOwner().equals(user) && !participantRepository.exists(user.getId(), campaignModel.getId())) {
            throw new IllegalArgumentException("You are not part of this Campaign");
        }


        return null;
    }

    @Override
    public CampaignPackageResponseDto createByCharacter(Long characterId, CampaignPackageCreateRequestDto campaignPackageDto, String username) {
        return null;
    }

    @Override
    public CampaignPackageResponseDto post(Long packageId, CampaignPackagePostRequestDto campaignPackageDto, String username) {
        return null;
    }

    @Override
    public String delete(Long packageId, String username) {
        return null;
    }

    @Override
    public Collection<CampaignPackageResponseDto> get(Long campaignId, Long parentPackageId, String username) {
        return null;
    }

    @Override
    public CampaignFileResponseDto createFile(Long packageId, CampaignFileTypeEnum campaignFileTypeEnum, CampaignFileCreateRequestDto campaignFileDto, String username) {
        return null;
    }

    @Override
    public CampaignFileResponseDto updateFile(Long campaignFileId, CampaignFileTypeEnum campaignFileTypeEnum, CampaignFilePostRequestDto campaignFileDto, String username) {
        return null;
    }

    @Override
    public String deleteFile(Long campaignFileId, String username) {
        return null;
    }

    @Override
    public Collection<CampaignFileResponseDto> getFile(Long campaignId, Long parentPackageId, String username) {
        return null;
    }
}
