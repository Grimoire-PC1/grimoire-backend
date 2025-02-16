package com.grimoire.controller;

import com.grimoire.controller.documentation.EngineController;
import com.grimoire.dto.engine.EngineCreateRequestDto;
import com.grimoire.dto.engine.EngineEditRequestDto;
import com.grimoire.dto.engine.EngineResponseDto;
import com.grimoire.dto.engine.EngineTypeEnum;
import com.grimoire.service.service.EngineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/engine")
@Validated
@CrossOrigin
@Slf4j
public class EngineControllerImpl implements EngineController {
    private final EngineService engineService;
    @Autowired
    public EngineControllerImpl(EngineService engineService) {
        this.engineService = engineService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> createEngine(
            @RequestParam(name = "tipo_sistema") EngineTypeEnum engineTypeEnum,
            @RequestBody EngineCreateRequestDto engineDto,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED).body(engineService.createEngine(authentication.getName(), engineDto, engineTypeEnum));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateEngine(
            @RequestParam(name = "id_sistema") Long idSys,
            @RequestParam(name = "tipo_sistema", required = false) EngineTypeEnum engineTypeEnum,
            @RequestBody EngineEditRequestDto engineDto,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(engineService.editEngine(idSys, authentication.getName(), engineDto, engineTypeEnum));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteEngine(
            @RequestParam(name = "id_sistema") Long idSys,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(engineService.deleteEngine(idSys, authentication.getName()));
    }

    @GetMapping("/get")
    public ResponseEntity<Collection<EngineResponseDto>> getUserEngine(
            @RequestParam(name = "id_sistema", required = false) Long idSys,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(engineService.getUserEngines(idSys, authentication.getName()));
    }

    @GetMapping("/get-public")
    public ResponseEntity<Collection<EngineResponseDto>> getPublicEngine(
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(engineService.getPublicEngines());
    }
}

