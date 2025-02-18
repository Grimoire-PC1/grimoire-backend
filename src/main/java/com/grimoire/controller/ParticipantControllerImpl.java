package com.grimoire.controller;

import com.grimoire.controller.documentation.ParticipantController;
import com.grimoire.dto.participant.ParticipantResponseDto;
import com.grimoire.service.service.ParticipantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/participant")
@Validated
@CrossOrigin
@Slf4j
public class ParticipantControllerImpl implements ParticipantController {
    private final ParticipantService participantService;

    @Autowired
    public ParticipantControllerImpl(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @PostMapping("/register")
    public ResponseEntity<ParticipantResponseDto> create(
            @RequestParam(name = "id_campanha") Long campaignId,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED).body(participantService.create(campaignId, authentication.getName()));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(
            @RequestParam(name = "id_campanha") Long campaignId,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(participantService.delete(campaignId, authentication.getName()));
    }

    @GetMapping("/get")
    public ResponseEntity<Collection<ParticipantResponseDto>> get(
            @RequestParam(name = "id_campanha") Long campaignId,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(participantService.get(campaignId, authentication.getName()));
    }
}
