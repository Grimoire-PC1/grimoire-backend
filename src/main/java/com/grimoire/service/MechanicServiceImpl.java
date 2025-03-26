package com.grimoire.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grimoire.dto.engine.EngineTypeEnum;
import com.grimoire.dto.mechanic.MechanicCreateRequestDto;
import com.grimoire.dto.mechanic.MechanicPostRequestDto;
import com.grimoire.dto.mechanic.MechanicResponseDto;
import com.grimoire.model.grimoire.*;
import com.grimoire.repository.*;
import com.grimoire.service.interfaces.MechanicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class MechanicServiceImpl implements MechanicService {
    private final MechanicRepository mechanicRepository;
    private final EngineRepository engineRepository;
    private final UserRepository userRepository;
    private final CampaignRepository campaignRepository;
    private final ParticipantRepository participantRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public MechanicServiceImpl(MechanicRepository mechanicRepository, EngineRepository engineRepository, UserRepository userRepository, CampaignRepository campaignRepository, ParticipantRepository participantRepository) {
        this.mechanicRepository = mechanicRepository;
        this.engineRepository = engineRepository;
        this.userRepository = userRepository;
        this.campaignRepository = campaignRepository;
        this.participantRepository = participantRepository;
    }

    @Override
    public MechanicResponseDto create(Long engineId, MechanicCreateRequestDto mechanicDto, String username) throws JsonProcessingException {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Authorization error"));
        EngineModel engine = engineRepository.findById(engineId)
                .orElseThrow(() -> new IllegalArgumentException("System not found: " + engineId));
        if (!engine.getOwner().equals(user)) {
            throw new AccessDeniedException("You don't have permission to this System");
        }

        MechanicModel mechanic = MechanicModel.builder()
                .engine(engine)
                .name(mechanicDto.getName())
                .description(mechanicDto.getDescription())
                .actions(objectMapper.writeValueAsString(mechanicDto.getActions()))
                .effects(objectMapper.writeValueAsString(mechanicDto.getEffects()))
                .build();

        return mechanicRepository.save(mechanic).toDto();
    }

    @Override
    public MechanicResponseDto edit(Long mechanicId, MechanicPostRequestDto mechanicDto, String username) throws JsonProcessingException {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Authorization error"));
        MechanicModel mechanic = mechanicRepository.findById(mechanicId)
                .orElseThrow(() -> new IllegalArgumentException("Mechanic not found: " + mechanicId));
        if (!mechanic.getEngine().getOwner().equals(user)) {
            throw new AccessDeniedException("You don't have permission to this Mechanic");
        }
        mechanic.setName(mechanicDto.getName() == null ? mechanic.getName() : mechanicDto.getName());
        mechanic.setDescription(mechanicDto.getDescription() == null ? mechanic.getDescription() : mechanicDto.getDescription());
        mechanic.setActions(mechanicDto.getActions() == null ? mechanic.getActions() : objectMapper.writeValueAsString(mechanicDto.getActions()));
        mechanic.setEffects(mechanicDto.getEffects() == null ? mechanic.getEffects() : objectMapper.writeValueAsString(mechanicDto.getEffects()));

        return mechanicRepository.save(mechanic).toDto();
    }

    @Override
    public String delete(Long mechanicId, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Authorization error"));
        MechanicModel mechanic = mechanicRepository.findById(mechanicId)
                .orElseThrow(() -> new IllegalArgumentException("Mechanic not found: " + mechanicId));
        if (!mechanic.getEngine().getOwner().equals(user)) {
            throw new AccessDeniedException("You don't have permission to this Mechanic");
        }
        mechanicRepository.delete(mechanic);

        return "Mechanic deleted successfully!";
    }

    @Override
    public Collection<MechanicResponseDto> get(Long engineId, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Authorization error"));
        EngineModel engine = engineRepository.findById(engineId)
                .orElseThrow(() -> new IllegalArgumentException("System not found: " + engineId));
        if (!( engine.getOwner().equals(user) ||
                EngineTypeEnum.fromId(engine.getEngineType().getId()).equals(EngineTypeEnum.PUBLICO)
        )) {
            throw new AccessDeniedException("You don't have permission to this System");
        }

        List<MechanicModel> mechanicModels = mechanicRepository.findAllFiltered(engineId);
        return mechanicModels.stream().map(MechanicModel::toDto).toList();
    }

    @Override
    public Collection<MechanicResponseDto> getByCampaign(Long campaignId, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Authorization error"));
        CampaignModel campaignModel = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new IllegalArgumentException("Campaign not found: " + campaignId));
        if (!campaignModel.getOwner().equals(user) && !participantRepository.exists(user.getId(), campaignModel.getId())) {
            throw new IllegalArgumentException("You are not part of this Campaign");
        }

        Collection<MechanicModel> models = mechanicRepository.
                findByCampaign(campaignModel.getEngine().getId());

        return models.stream().map(MechanicModel::toDto).toList();
    }
}
