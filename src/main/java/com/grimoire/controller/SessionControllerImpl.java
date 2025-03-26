package com.grimoire.controller;

import com.grimoire.controller.documentation.SessionController;
import com.grimoire.dto.session.SessionCreateRequestDto;
import com.grimoire.dto.session.SessionPostRequestDto;
import com.grimoire.dto.session.SessionResponseDto;
import com.grimoire.dto.session.SessionTypeEnum;
import com.grimoire.service.interfaces.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/session")
@Validated
@CrossOrigin
@Slf4j
public class SessionControllerImpl implements SessionController {
    private final SessionService sessionService;

    @Autowired
    public SessionControllerImpl(SessionService sessionService) {

        this.sessionService = sessionService;
    }

    @Override
    @PostMapping("/register")
    public ResponseEntity<SessionResponseDto> create(
            @RequestParam(name = "id_campanha") Long campaignId,
            @RequestParam(name = "tipo_sessao") SessionTypeEnum sessionTypeEnum,
            @Validated @RequestBody SessionCreateRequestDto sessionDto,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                sessionService.create(campaignId, sessionTypeEnum, sessionDto, authentication.getName()));
    }

    @Override
    @PutMapping("/update")
    public ResponseEntity<SessionResponseDto> update(
            @RequestParam(name = "id_sessao") Long sessionId,
            @RequestParam(name = "novo_tipo_sessao", required = false) SessionTypeEnum sessionTypeEnum,
            @Validated @RequestBody SessionPostRequestDto sessionDto,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(
                sessionService.update(sessionId, sessionTypeEnum, sessionDto, authentication.getName()));
    }

    @Override
    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(
            @RequestParam(name = "id_sessao") Long sessionId,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(
                sessionService.delete(sessionId, authentication.getName()));
    }

    @Override
    @GetMapping("/get/campaign")
    public ResponseEntity<Collection<SessionResponseDto>> getByCampaign(
            @RequestParam(name = "id_campanha") Long campaignId,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(
                sessionService.getByCampaign(campaignId, authentication.getName()));
    }
}
