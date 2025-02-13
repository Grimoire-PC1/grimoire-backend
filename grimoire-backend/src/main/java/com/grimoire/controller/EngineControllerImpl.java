package com.grimoire.controller;

import com.grimoire.controller.documentation.EngineController;
import com.grimoire.dto.engine.EngineCreateRequestDto;
import com.grimoire.dto.engine.EngineEditRequestDto;
import com.grimoire.dto.engine.EngineResponseDto;
import com.grimoire.service.service.AuthService;
import com.grimoire.service.service.EngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/engine")
public class EngineControllerImpl implements EngineController {
    private final EngineService engineService;
    @Autowired
    public EngineControllerImpl(EngineService engineService, AuthService authService) {
        this.engineService = engineService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> createEngine(
            @Validated @RequestBody EngineCreateRequestDto engineDto,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED).body(engineService.createEngine(authentication.getName(), engineDto));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateEngine(
            @Validated @RequestBody EngineEditRequestDto engineDto,
            Long idSys, Long idUser) {
        return ResponseEntity.status(HttpStatus.OK).body(engineService.editEngine(idSys, idUser, engineDto));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteEngine(
            Long idSys, Long idUser) {
        return ResponseEntity.status(HttpStatus.OK).body(engineService.deleteEngine(idSys, idUser));
    }

    @GetMapping("/get")
    public ResponseEntity<EngineResponseDto> getEngine(
            Long idSys) {
        return ResponseEntity.status(HttpStatus.OK).body(engineService.getEngine(idSys));
    }
}

