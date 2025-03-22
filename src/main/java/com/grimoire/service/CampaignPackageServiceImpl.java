package com.grimoire.service;

import com.grimoire.dto.campaignPackage.*;
import com.grimoire.model.grimoire.*;
import com.grimoire.model.grimoire.typeTables.FileTypeModel;
import com.grimoire.repository.*;
import com.grimoire.service.interfaces.CampaignPackageService;
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
    private final ItemRepository itemRepository;

    @Autowired
    public CampaignPackageServiceImpl(CampaignPackageRepository campaignPackageRepository, CampaignFileRepository campaignFileRepository, CharacterRepository characterRepository, CampaignRepository campaignRepository, ParticipantRepository participantRepository, UserRepository userRepository, ItemRepository itemRepository) {
        this.campaignPackageRepository = campaignPackageRepository;
        this.campaignFileRepository = campaignFileRepository;
        this.characterRepository = characterRepository;
        this.campaignRepository = campaignRepository;
        this.participantRepository = participantRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
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

        CampaignPackageModel parent = getPackage(campaignPackageDto.getIdParentPackage(), user);

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

        CampaignPackageModel parent = getPackage(campaignPackageDto.getIdParentPackage(), user);

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
        CampaignPackageModel campaignPackage = getPackage(packageId, user);

        CampaignPackageModel parent = getPackage(campaignPackageDto.getIdParentPackage(), user);

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
        CampaignPackageModel campaignPackage = getPackage(packageId, user);
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
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
        CampaignPackageModel campaignPackage = getPackage(packageId, user);

        CampaignFileModel model = CampaignFileModel.builder()
                .campaignPackage(campaignPackage)
                .fileType(new FileTypeModel(campaignFileTypeEnum))
                .name(campaignFileDto.getName())
                .build();

        switch (campaignFileTypeEnum) {
            case CampaignFileTypeEnum.TEXTO:
                model.setText(campaignFileDto.getContent());
                break;
            case CampaignFileTypeEnum.IMAGEM:
                model.setIdPicture(campaignFileDto.getContent());
                break;
            case CampaignFileTypeEnum.ITEM:
                ItemModel item = itemRepository.findById(Long.valueOf(campaignFileDto.getContent()))
                        .orElseThrow(() -> new IllegalArgumentException("Item not found: " + Long.valueOf(campaignFileDto.getContent())));
                if (!item.getCampaign().getOwner().equals(user)) {
                    throw new IllegalArgumentException("You don't have permission to this Item");
                }
                model.setItem(item);
                break;
            case CampaignFileTypeEnum.PERSONAGEM:
                CharacterModel character = characterRepository.findById(Long.valueOf(campaignFileDto.getContent()))
                        .orElseThrow(() -> new IllegalArgumentException("Character not found: " + Long.valueOf(campaignFileDto.getContent())));
                if (!character.getUser().equals(user) && !character.getCampaign().getOwner().equals(user)) {
                    throw new IllegalArgumentException("You don't have permission to this Character");
                };
                model.setCharacter(character);
                break;
        }

        return campaignFileRepository.save(model).toDto();
    }

    @Override
    public CampaignFileResponseDto updateFile(Long campaignFileId, CampaignFilePostRequestDto campaignFileDto, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
        CampaignFileModel campaignFile = campaignFileRepository.findById(campaignFileId)
                .orElseThrow(() -> new IllegalArgumentException("Campaign File not found: " + campaignFileId));
        CampaignPackageModel campaignPackage = campaignFile.getCampaignPackage();
        if (! (campaignPackage.getCampaign().getOwner().equals(user)
                || campaignPackage.getUserModel().equals(user)
        )) {
            throw new IllegalArgumentException("You don't have permission to this File");
        }

        campaignFile.setName(campaignFileDto.getName() == null ? campaignFile.getName() : campaignFileDto.getName());
        campaignPackage = getPackage(campaignFileDto.getIdParentPackage(), user);
        campaignFile.setCampaignPackage(campaignPackage == null ? campaignFile.getCampaignPackage() : campaignPackage);
        if (campaignFileDto.getContent() != null) {
            switch (CampaignFileTypeEnum.fromId(campaignFile.getFileType().getId())) {
                case CampaignFileTypeEnum.TEXTO:
                    campaignFile.setText(campaignFileDto.getContent());
                    break;
                case CampaignFileTypeEnum.IMAGEM:
                    campaignFile.setIdPicture(campaignFileDto.getContent());
                    break;
                case CampaignFileTypeEnum.ITEM:
                    ItemModel item = itemRepository.findById(Long.valueOf(campaignFileDto.getContent()))
                            .orElseThrow(() -> new IllegalArgumentException("Item not found: " + Long.valueOf(campaignFileDto.getContent())));
                    if (!item.getCampaign().getOwner().equals(user)) {
                        throw new IllegalArgumentException("You don't have permission to this Item");
                    }
                    campaignFile.setItem(item);
                    break;
                case CampaignFileTypeEnum.PERSONAGEM:
                    CharacterModel character = characterRepository.findById(Long.valueOf(campaignFileDto.getContent()))
                            .orElseThrow(() -> new IllegalArgumentException("Character not found: " + Long.valueOf(campaignFileDto.getContent())));
                    if (!character.getUser().equals(user) && !character.getCampaign().getOwner().equals(user)) {
                        throw new IllegalArgumentException("You don't have permission to this Character");
                    };
                    campaignFile.setCharacter(character);
                    break;
            }
        }

        return campaignFileRepository.save(campaignFile).toDto();
    }

    @Override
    public String deleteFile(Long campaignFileId, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
        CampaignFileModel campaignFile = campaignFileRepository.findById(campaignFileId)
                .orElseThrow(() -> new IllegalArgumentException("Campaign File not found: " + campaignFileId));
        CampaignPackageModel campaignPackage = campaignFile.getCampaignPackage();
        if (! (campaignPackage.getCampaign().getOwner().equals(user)
                || campaignPackage.getUserModel().equals(user)
        )) {
            throw new IllegalArgumentException("You don't have permission to this File");
        }

        campaignFileRepository.delete(campaignFile);
        return "File deleted successfully!";
    }

    @Override
    public Collection<CampaignFileResponseDto> getFile(Long campaignId, Long parentPackageId, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
        Collection<CampaignFileModel> campaignFiles = campaignFileRepository.findAllFiltered(campaignId, parentPackageId, user.getId());

        return campaignFiles.stream().map(CampaignFileModel::toDto).toList();
    }

    private CampaignPackageModel getPackage(Long packageId, UserModel user) {
        CampaignPackageModel packageModel = null;
        if (packageId != null) {
            packageModel = campaignPackageRepository.findById(packageId)
                    .orElseThrow(() -> new IllegalArgumentException("Campaign Package not found: " + packageId));
            if (!(  packageModel.getUserModel().equals(user)
                    || packageModel.getCampaign().getOwner().equals(user)
            )) {
                throw new IllegalArgumentException("You don't have permission to the Package: " + packageModel.getId());
            }
        }
        return packageModel;
    }


}
