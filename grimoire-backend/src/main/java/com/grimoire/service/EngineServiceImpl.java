package com.grimoire.service;

import com.grimoire.dto.engine.EngineCreateRequestDto;
import com.grimoire.dto.engine.EngineEditRequestDto;
import com.grimoire.dto.engine.EngineResponseDto;
import com.grimoire.model.grimoire.EngineModel;
import com.grimoire.repository.EngineRepository;
import com.grimoire.service.service.EngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EngineServiceImpl implements EngineService {

    private final EngineRepository engineRepository;

    @Autowired
    public EngineServiceImpl(EngineRepository engineRepository, PasswordEncoder passwordEncoder) {
        this.engineRepository = engineRepository;
    }

    @Override
    @Transactional
    public String createEngine(EngineCreateRequestDto engineDTO) {
        if (engineRepository.existsByName(engineDTO.getName())) {
            throw new RuntimeException("System already exists!");
        }
        EngineModel engine = EngineModel.builder()
                .idUser(engineDTO.getIdUser())
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
    public String editEngine(String name, EngineEditRequestDto engineDTO) {
        EngineModel engine = engineRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("System not found: " + name));
        engine.setName(engineDTO.getName().isBlank() ? engine.getName() : engineDTO.getName());
        engine.setDescription(engineDTO.getDescription().isBlank() ? engine.getDescription() : engineDTO.getDescription());
        engine.setPictureUrl(engineDTO.getPictureUrl().isBlank() ? engine.getPictureUrl() : engineDTO.getPictureUrl());
        engine.setTypeSys(engineDTO.getTypeSys().isBlank() ? engine.getTypeSys() : engineDTO.getTypeSys());

        engineRepository.save(engine);
        return "System updated successfully!";
    }

    @Override
    @Transactional
    public String deleteEngine(String name) {
        EngineModel engine = engineRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("System not found: " + name));

        engineRepository.delete(engine);
        return "System deleted successfully!";
    }

    @Override
    public EngineResponseDto getEngine(String name) {
        EngineModel engine = engineRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("System not found: " + name));

        return engine.toDto();
    }
}
