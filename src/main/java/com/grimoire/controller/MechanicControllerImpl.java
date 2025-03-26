package com.grimoire.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.grimoire.controller.documentation.MechanicController;
import com.grimoire.dto.engineRule.RuleResponseDto;
import com.grimoire.dto.mechanic.MechanicCreateRequestDto;
import com.grimoire.dto.mechanic.MechanicPostRequestDto;
import com.grimoire.dto.mechanic.MechanicResponseDto;
import com.grimoire.service.interfaces.MechanicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/mechanic")
@Validated
@CrossOrigin
@Slf4j
public class MechanicControllerImpl implements MechanicController {
    private final MechanicService mechanicService;

    @Autowired
    public MechanicControllerImpl(MechanicService mechanicService) {
        this.mechanicService = mechanicService;
    }

    @PostMapping("/register")
    public ResponseEntity<MechanicResponseDto> create(
            @RequestParam(name = "id_sistema") Long engineId,
            @Validated @RequestBody MechanicCreateRequestDto mechanicDto,
            Authentication authentication) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                mechanicService.create(engineId, mechanicDto, authentication.getName()));
    }

    @PutMapping("/update")
    public ResponseEntity<MechanicResponseDto> update(
            @RequestParam(name = "id_mecanica") Long mechanicId,
            @Validated @RequestBody MechanicPostRequestDto mechanicDto,
            Authentication authentication) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK).body(
                mechanicService.edit(mechanicId, mechanicDto, authentication.getName()));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(
            @RequestParam(name = "id_mecanica") Long mechanicId,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(
                mechanicService.delete(mechanicId, authentication.getName()));
    }

    @GetMapping("/get")
    public ResponseEntity<Collection<MechanicResponseDto>> get(
            @RequestParam(name = "id_sistema") Long engineId,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(
                mechanicService.get(engineId, authentication.getName()));
    }

    @Override
    @GetMapping("/get/campaign")
    public ResponseEntity<Collection<MechanicResponseDto>> getByCampaign(
            @RequestParam(name = "id_campanha") Long campaignId,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(
                mechanicService.getByCampaign(campaignId, authentication.getName()));
    }
}
