package com.grimoire.service;

import com.grimoire.dto.campaignPackage.*;
import com.grimoire.model.grimoire.*;
import com.grimoire.model.grimoire.typeTables.FileTypeModel;
import com.grimoire.repository.*;
import com.grimoire.service.service.CampaignPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class CampaignPackageServiceImpl implements CampaignPackageService {
    private final CampaignPackageRepository campaignPackageRepository;
    private final CampaignFileRepository campaignFileRepository;
    private final CharacterRepository characterRepository;
    private final CampaignRepository campaignRepository;
    private final ParticipantRepository participantRepository;
    private final UserRepository userRepository;

    @Autowired
    public CampaignPackageServiceImpl(CampaignPackageRepository campaignPackageRepository, CampaignFileRepository campaignFileRepository, CharacterRepository characterRepository, CampaignRepository campaignRepository, ParticipantRepository participantRepository, UserRepository userRepository) {
        this.campaignPackageRepository = campaignPackageRepository;
        this.campaignFileRepository = campaignFileRepository;
        this.characterRepository = characterRepository;
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

        CampaignPackageModel parent = getParent(campaignPackageDto, user);

        CampaignPackageModel campaignPackage = CampaignPackageModel.builder()
                .userModel(user)
                .campaign(campaignModel)
                .parentPackage(parent)
                .name(campaignPackageDto.getName())
                .isPublic(isPublic)
                .build();

        return campaignPackageRepository.save(campaignPackage).toDto();
    }

    @Override
    public CampaignPackageResponseDto createByCharacter(Long characterId, CampaignPackageCreateRequestDto campaignPackageDto, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
        CharacterModel character = characterRepository.findById(characterId)
                .orElseThrow(() -> new IllegalArgumentException("Character not found: " + characterId));
        if (!character.getUser().equals(user) && !character.getCampaign().getOwner().equals(user)) {
            throw new IllegalArgumentException("You don't have permission to this Character");
        }

        CampaignPackageModel parent = getParent(campaignPackageDto, user);

        CampaignPackageModel campaignPackage = CampaignPackageModel.builder()
                .userModel(user)
                .campaign(character.getCampaign())
                .parentPackage(parent)
                .name(campaignPackageDto.getName())
                .isPublic(false)
                .build();
        campaignPackage = campaignPackageRepository.save(campaignPackage);

        CampaignFileModel campaignFile = CampaignFileModel.builder()
                .campaignPackage(campaignPackage)
                .fileType(new FileTypeModel(CampaignFileTypeEnum.PERSONAGEM))
                .name(character.getName())
                .character(character)
                .build();
        campaignFileRepository.save(campaignFile);

        return campaignPackage.toDto();
    }

    @Override
    public CampaignPackageResponseDto post(Long packageId, CampaignPackagePostRequestDto campaignPackageDto, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
        CampaignPackageModel campaignPackage = campaignPackageRepository.findById(packageId)
                .orElseThrow(() -> new IllegalArgumentException("Campaign Package not found: " + packageId));

        if (! (campaignPackage.getCampaign().getOwner().equals(user)
            || campaignPackage.getUserModel().equals(user)
        )) {
            throw new IllegalArgumentException("You don't have permission to this Package");
        }

        CampaignPackageModel parent = getParent(campaignPackageDto, user);

        campaignPackage.setName(
                campaignPackageDto.getName() == null ? campaignPackage.getName() : campaignPackageDto.getName());
        campaignPackage.setIsPublic(
                campaignPackageDto.getIsPublic() == null ? campaignPackage.getIsPublic() : campaignPackageDto.getIsPublic());
        campaignPackage.setParentPackage(
                parent == null ? campaignPackage.getParentPackage() : parent);

        return campaignPackageRepository.save(campaignPackage).toDto();
    }

    @Override
    public String delete(Long packageId, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
        CampaignPackageModel campaignPackage = campaignPackageRepository.findById(packageId)
                .orElseThrow(() -> new IllegalArgumentException("Campaign Package not found: " + packageId));

        if (! (campaignPackage.getCampaign().getOwner().equals(user)
                || campaignPackage.getUserModel().equals(user)
        )) {
            throw new IllegalArgumentException("You don't have permission to this Package");
        }
        campaignPackageRepository.delete(campaignPackage);
        return "Campaign Package deleted successfully!";
    }

    @Override
    public Collection<CampaignPackageResponseDto> get(Long campaignId, Long parentPackageId, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
        CampaignModel campaignModel = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new IllegalArgumentException("Campaign not found: " + campaignId));
        if (!campaignModel.getOwner().equals(user) && !participantRepository.exists(user.getId(), campaignModel.getId())) {
            throw new IllegalArgumentException("You are not part of this Campaign");
        }

        List<CampaignPackageModel> models = campaignPackageRepository.findAllFiltered(campaignId, parentPackageId, user.getId());
        return models.stream().map(CampaignPackageModel::toDto).toList();
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

    private CampaignPackageModel getParent(CampaignPackageCreateRequestDto campaignPackageDto, UserModel user) {
        CampaignPackageModel parent = null;
        if (campaignPackageDto.getIdParentPackage() != null) {
            parent = campaignPackageRepository.findById(campaignPackageDto.getIdParentPackage())
                    .orElseThrow(() -> new IllegalArgumentException("Campaign Package not found: " + campaignPackageDto.getIdParentPackage()));
            if (!(  parent.getUserModel().equals(user)
                    || parent.getCampaign().getOwner().equals(user)
            )) {
                throw new IllegalArgumentException("You don't have permission to the Package: " + parent.getId());
            }
        }
        return parent;
    }

    private CampaignPackageModel getParent(CampaignPackagePostRequestDto campaignPackageDto, UserModel user) {
        CampaignPackageModel parent = null;
        if (campaignPackageDto.getIdParentPackage() != null) {
            parent = campaignPackageRepository.findById(campaignPackageDto.getIdParentPackage())
                    .orElseThrow(() -> new IllegalArgumentException("Campaign Package not found: " + campaignPackageDto.getIdParentPackage()));
            if (!(  parent.getUserModel().equals(user)
                    || parent.getCampaign().getOwner().equals(user)
            )) {
                throw new IllegalArgumentException("You don't have permission to the Package: " + parent.getId());
            }
        }
        return parent;
    }
}
