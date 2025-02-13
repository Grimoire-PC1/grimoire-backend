package com.grimoire.service;

import com.grimoire.dto.engine.EngineCreateRequestDto;
import com.grimoire.dto.engine.EngineEditRequestDto;
import com.grimoire.dto.engine.EngineResponseDto;
import com.grimoire.model.grimoire.EngineModel;
import com.grimoire.model.grimoire.UserModel;
import com.grimoire.repository.EngineRepository;
import com.grimoire.repository.UserRepository;
import com.grimoire.service.service.EngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public String createEngine(String username, EngineCreateRequestDto engineDTO) {
        if (engineRepository.existsByName(engineDTO.getName())) {
            throw new RuntimeException("System already exists!");
        }
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
        EngineModel engine = EngineModel.builder()
                .idUser(user.getId())
                .name(engineDTO.getName())
                .description(engineDTO.getDescription())
                .pictureUrl(engineDTO.getPictureUrl())
                .typeSys(engineDTO.getTypeSys())
                .build();

        engineRepository.save(engine);
        return "System registered successfully!";
    }

    @Override
    @Transactional
    public String editEngine(Long idSys, Long idUser, EngineEditRequestDto engineDTO) {
        EngineModel engine = engineRepository.findById(idSys)
                .orElseThrow(() -> new IllegalArgumentException("System not found: " + idSys));
        if (!engine.getIdUser().equals(idUser)) {
            throw new IllegalArgumentException("You don't have permission to delete System");
        }
        engine.setName(engineDTO.getName().isBlank() ? engine.getName() : engineDTO.getName());
        engine.setDescription(engineDTO.getDescription().isBlank() ? engine.getDescription() : engineDTO.getDescription());
        engine.setPictureUrl(engineDTO.getPictureUrl().isBlank() ? engine.getPictureUrl() : engineDTO.getPictureUrl());
        engine.setTypeSys(engineDTO.getTypeSys().isBlank() ? engine.getTypeSys() : engineDTO.getTypeSys());

        engineRepository.save(engine);
        return "System updated successfully!";
    }

    @Override
    @Transactional
    public String deleteEngine(Long idSys, Long idUser) {
        EngineModel engine = engineRepository.findById(idSys)
                .orElseThrow(() -> new IllegalArgumentException("System not found: " + idSys));

        if (!engine.getIdUser().equals(idUser)) {
            throw new IllegalArgumentException("You don't have permission to delete System");
        }
        engineRepository.delete(engine);
        return "System deleted successfully!";
    }

    @Override
    public EngineResponseDto getEngine(Long idSys) {
        EngineModel engine = engineRepository.findById(idSys)
                .orElseThrow(() -> new IllegalArgumentException("System not found: " + idSys));

        return engine.toDto();
    }
}

