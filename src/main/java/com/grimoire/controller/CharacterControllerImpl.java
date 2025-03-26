package com.grimoire.controller;

import com.grimoire.controller.documentation.CharacterController;
import com.grimoire.dto.character.CharacterCreateRequestDto;
import com.grimoire.dto.character.CharacterPostRequestDto;
import com.grimoire.dto.character.CharacterResponseDto;
import com.grimoire.service.interfaces.CharacterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/character")
@Validated
@CrossOrigin
@Slf4j
public class CharacterControllerImpl implements CharacterController {
    private final CharacterService characterService;

    @Autowired
    public CharacterControllerImpl(CharacterService characterService) {
        this.characterService = characterService;
    }

    @PostMapping("/register")
    public ResponseEntity<CharacterResponseDto> create(
            @RequestParam(name = "id_campanha") Long campaignId,
            @Validated @RequestBody CharacterCreateRequestDto characterDto,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED).body(characterService.create(campaignId, characterDto, authentication.getName()));
    }

    @PutMapping("/update")
    public ResponseEntity<CharacterResponseDto> update(
            @RequestParam(name = "id_personagem") Long characterId,
            @Validated @RequestBody CharacterPostRequestDto characterDto,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(
                characterService.edit(characterId, characterDto, authentication.getName()));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(
            @RequestParam(name = "id_personagem") Long characterId,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(
                characterService.delete(characterId, authentication.getName()));
    }

    @GetMapping("/get/user")
    public ResponseEntity<Collection<CharacterResponseDto>> getByUser(
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(
                characterService.getByUser(authentication.getName()));
    }

    @GetMapping("/get/campaign")
    public ResponseEntity<Collection<CharacterResponseDto>> getByCampaign(
            @RequestParam(name = "id_campanha") Long campaignId,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(
                characterService.getByCampaign(campaignId, authentication.getName()));
    }

    @GetMapping("/get/campaign/others")
    public ResponseEntity<Collection<CharacterResponseDto>> getOthersByCampaign(
            @RequestParam(name = "id_campanha") Long campaignId,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(
                characterService.getOthersByCampaign(campaignId, authentication.getName()));
    }
}
