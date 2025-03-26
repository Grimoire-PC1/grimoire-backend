package com.grimoire.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.grimoire.controller.documentation.CharacterSheetContentController;
import com.grimoire.dto.characterSheetContent.CharacterSheetContentCreateRequestDto;
import com.grimoire.dto.characterSheetContent.CharacterSheetContentPostRequestDto;
import com.grimoire.dto.characterSheetContent.CharacterSheetContentResponseDto;
import com.grimoire.service.interfaces.CharacterSheetContentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/character-sheet-content")
@Validated
@CrossOrigin
@Slf4j
public class CharacterSheetContentControllerImpl implements CharacterSheetContentController {

    CharacterSheetContentService characterSheetContentService;

    @Autowired
    public CharacterSheetContentControllerImpl(CharacterSheetContentService characterSheetContentService) {
        this.characterSheetContentService = characterSheetContentService;
    }

    @Override
    @PostMapping("/register")
    public ResponseEntity<CharacterSheetContentResponseDto> create(
            @RequestParam(name = "id_personagem") Long characterId,
            @RequestParam(name = "id_sub_aba_ficha") Long characterSheetSubTabId,
            @Validated @RequestBody CharacterSheetContentCreateRequestDto characterSheetDto,
            Authentication authentication) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                characterSheetContentService.create(characterId, characterSheetSubTabId, characterSheetDto, authentication.getName()));
    }

    @Override
    @PutMapping("/update")
    public ResponseEntity<CharacterSheetContentResponseDto> update(
            @RequestParam(name = "id_conteudo_ficha") Long characterSheetContentId,
            @Validated @RequestBody CharacterSheetContentPostRequestDto characterSheetDto,
            Authentication authentication) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK).body(
                characterSheetContentService.edit(characterSheetContentId, characterSheetDto, authentication.getName()));    }

    @Override
    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(
            @RequestParam(name = "id_conteudo_ficha") Long characterSheetContentId,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(
                characterSheetContentService.delete(characterSheetContentId, authentication.getName()));
    }

    @Override
    @GetMapping("/get")
    public ResponseEntity<Collection<CharacterSheetContentResponseDto>> get(
            @RequestParam(name = "id_personagem") Long characterId,
            @RequestParam(name = "id_aba_ficha") Long characterSheetTabId,
            @RequestParam(name = "id_sub_aba_ficha") Long characterSheetSubTabId,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(
                characterSheetContentService.get(characterId, characterSheetTabId, characterSheetSubTabId, authentication.getName()));
    }
}
