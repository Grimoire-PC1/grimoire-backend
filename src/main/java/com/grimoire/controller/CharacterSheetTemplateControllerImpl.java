package com.grimoire.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.grimoire.controller.documentation.CharacterSheetTemplateController;
import com.grimoire.dto.characterSheetTemplate.*;
import com.grimoire.service.service.CharacterSheetTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/character-sheet-template")
@Validated
@CrossOrigin
@Slf4j
public class CharacterSheetTemplateControllerImpl implements CharacterSheetTemplateController {
    CharacterSheetTemplateService characterSheetTemplateService;

    @Autowired
    public CharacterSheetTemplateControllerImpl(CharacterSheetTemplateService characterSheetTemplateService) {
        this.characterSheetTemplateService = characterSheetTemplateService;
    }

    @Override
    @PostMapping("/register/tab")
    public ResponseEntity<CharacterSheetTabResponseDto> createTab(
            @RequestParam(name = "id_sistema") Long engineId,
            @Validated @RequestBody CharacterSheetTabCreateRequestDto characterSheetTabDto,
            Authentication authentication) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                characterSheetTemplateService.createTab(engineId, characterSheetTabDto, authentication.getName()));
    }

    @Override
    @PutMapping("/update/tab")
    public ResponseEntity<CharacterSheetTabResponseDto> updateTab(
            @RequestParam(name = "id_aba_ficha") Long characterSheetTabId,
            @Validated @RequestBody CharacterSheetTabPostRequestDto characterSheetTabDto,
            Authentication authentication) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK).body(
                characterSheetTemplateService.editTab(characterSheetTabId, characterSheetTabDto, authentication.getName()));
    }

    @Override
    @DeleteMapping("/delete/tab")
    public ResponseEntity<String> deleteTab(
            @RequestParam(name = "id_aba_ficha") Long characterSheetTabId,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(
                characterSheetTemplateService.deleteTab(characterSheetTabId, authentication.getName()));
    }

    @Override
    @GetMapping("/get/tab")
    public ResponseEntity<Collection<CharacterSheetTabResponseDto>> getTab(
            @RequestParam(name = "id_sistema") Long engineId,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(
                characterSheetTemplateService.getTab(engineId, authentication.getName()));
    }

    @Override
    @GetMapping("/get/tab/campaign")
    public ResponseEntity<Collection<CharacterSheetTabResponseDto>> getTabByCampaign(
            @RequestParam(name = "id_campanha") Long campaignId,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(
                characterSheetTemplateService.getTabByCampaign(campaignId, authentication.getName()));
    }

    @Override
    @PostMapping("/register/sub-tab")
    public ResponseEntity<CharacterSheetSubTabResponseDto> createSubTab(
            @RequestParam(name = "id_aba_ficha") Long characterSheetTabId,
            @RequestParam(name = "tipo_sub_aba") CharacterSheetSubTabTypeEnum characterSheetSubTabTypeEnum,
            @Validated @RequestBody CharacterSheetSubTabCreateRequestDto characterSheetSubTabDto,
            Authentication authentication) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                characterSheetTemplateService.createSubTab(characterSheetTabId, characterSheetSubTabTypeEnum, characterSheetSubTabDto, authentication.getName()));
    }

    @Override
    @PutMapping("/update/sub-tab")
    public ResponseEntity<CharacterSheetSubTabResponseDto> updateSubTab(
            @RequestParam(name = "id_sub_aba_ficha") Long characterSheetSubTabId,
            @Validated @RequestBody CharacterSheetSubTabPostRequestDto characterSheetSubTabDto,
            Authentication authentication) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK).body(
                characterSheetTemplateService.editSubTab(characterSheetSubTabId, characterSheetSubTabDto, authentication.getName()));
    }

    @Override
    @DeleteMapping("/delete/sub-tab")
    public ResponseEntity<String> deleteSubTab(
            @RequestParam(name = "id_sub_aba_ficha") Long characterSheetSubTabId,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(
                characterSheetTemplateService.deleteSubTab(characterSheetSubTabId, authentication.getName()));
    }

    @Override
    @GetMapping("/get/sub-tab")
    public ResponseEntity<Collection<CharacterSheetSubTabResponseDto>> getSubTab(
            @RequestParam(name = "id_sistema") Long engineId,
            @RequestParam(name = "id_aba_ficha", required = false) Long characterSheetTabId,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(
                characterSheetTemplateService.getSubTab(engineId, characterSheetTabId, authentication.getName()));
    }

    @Override
    @GetMapping("/get/sub-tab/campaign")
    public ResponseEntity<Collection<CharacterSheetSubTabResponseDto>> getSubTabByCampaign(
            @RequestParam(name = "id_campanha") Long campaignId,
            @RequestParam(name = "id_aba_ficha", required = false) Long characterSheetTabId,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(
                characterSheetTemplateService.getSubTabByCampaign(campaignId, characterSheetTabId, authentication.getName()));
    }
}
