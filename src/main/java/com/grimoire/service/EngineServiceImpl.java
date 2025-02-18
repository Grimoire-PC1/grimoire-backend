package com.grimoire.service;

import com.grimoire.dto.engine.EngineCreateRequestDto;
import com.grimoire.dto.engine.EngineEditRequestDto;
import com.grimoire.dto.engine.EngineResponseDto;
import com.grimoire.dto.engine.EngineTypeEnum;
import com.grimoire.model.grimoire.EngineModel;
import com.grimoire.model.grimoire.UserModel;
import com.grimoire.model.joinTables.EngineTypeModel;
import com.grimoire.repository.EngineRepository;
import com.grimoire.repository.UserRepository;
import com.grimoire.service.service.EngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class EngineServiceImpl implements EngineService {

    private final EngineRepository engineRepository;
    private final UserRepository userRepository;

    @Autowired
    public EngineServiceImpl(EngineRepository engineRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.engineRepository = engineRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public EngineResponseDto createEngine(String username, EngineCreateRequestDto engineDTO, EngineTypeEnum engineTypeEnum) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
        EngineModel engine = EngineModel.builder()
                .owner(user)
                .name(engineDTO.getName())
                .description(engineDTO.getDescription())
                .pictureUrl(engineDTO.getPictureUrl())
                .engineType(new EngineTypeModel(engineTypeEnum))
                .build();

        engineRepository.save(engine);
        return engine.toDto();
    }

    @Override
    @Transactional
    public EngineResponseDto editEngine(
            Long idSys, String username, EngineEditRequestDto engineDTO, EngineTypeEnum engineTypeEnum
    ) {
        EngineModel engine = checkAccess(idSys, username);

        engine.setName(engineDTO.getName().isBlank() ? engine.getName() : engineDTO.getName());
        engine.setEngineType(engineTypeEnum == null ? engine.getEngineType() : new EngineTypeModel(engineTypeEnum));
        engine.setDescription(engineDTO.getDescription().isBlank() ? engine.getDescription() : engineDTO.getDescription());
        engine.setPictureUrl(engineDTO.getPictureUrl().isBlank() ? engine.getPictureUrl() : engineDTO.getPictureUrl());

        engineRepository.save(engine);
        return engine.toDto();
    }

    @Override
    @Transactional
    public String deleteEngine(Long idSys, String username) {
        EngineModel engine = checkAccess(idSys, username);
        engineRepository.delete(engine);
        return "Engine deleted successfully!";
    }

    @Override
    public Collection<EngineResponseDto> getUserEngines(Long idSys, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
        Collection<EngineModel> engines = engineRepository.findAllFiltered(idSys, user.getId());

        return engines.stream().map(EngineModel::toDto).toList();

    }

    @Override
    public Collection<EngineResponseDto> getPublicEngines() {
        Collection<EngineModel> engines = engineRepository.findAllByEngineType_Id(EngineTypeEnum.PUBLICO.getId());

        return engines.stream().map(EngineModel::toDto).toList();
    }

    private EngineModel checkAccess(Long idSys, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
        EngineModel engine = engineRepository.findById(idSys)
                .orElseThrow(() -> new IllegalArgumentException("System not found: " + idSys));

        if (!engine.getOwner().equals(user)) {
            throw new AccessDeniedException("You don't have permission to this System");
        }

        return engine;
    }

}

