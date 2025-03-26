package com.grimoire.controller;

import com.grimoire.controller.documentation.CampaignController;
import com.grimoire.dto.campaign.CampaignCreateRequestDto;
import com.grimoire.dto.campaign.CampaignPostRequestDto;
import com.grimoire.dto.campaign.CampaignResponseDto;
import com.grimoire.service.interfaces.CampaignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/campaign")
@Validated
@CrossOrigin
@Slf4j
public class CampaignControllerImpl implements CampaignController {
    private final CampaignService campaignService;
    @Autowired
    public CampaignControllerImpl(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @PostMapping("/register")
    public ResponseEntity<CampaignResponseDto> createCampaign(
            @Validated @RequestBody CampaignCreateRequestDto campaignDto,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED).body(campaignService.createCampaign(campaignDto, authentication.getName()));
    }

    @PutMapping("/update")
    public ResponseEntity<CampaignResponseDto> updateCampaign(
            @RequestParam(name = "id_campanha") Long idCampaign,
            @Validated @RequestBody CampaignPostRequestDto campaignDto,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(campaignService.postCampaign(idCampaign, campaignDto, authentication.getName()));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCampaign(
            @RequestParam(name = "id_campanha") Long idCampaign,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(campaignService.deleteCampaign(idCampaign, authentication.getName()));
    }

    @GetMapping("/get")
    public ResponseEntity<Collection<CampaignResponseDto>> getUserCampaigns(
            @RequestParam(name = "id_campanha", required = false) Long idCampaign,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(campaignService.getCampaign(idCampaign, authentication.getName()));
    }

    @GetMapping("/get-participating")
    public ResponseEntity<Collection<CampaignResponseDto>> getUserParticipatingCampaigns(
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(campaignService.getParticipatingCampaigns(authentication.getName()));
    }
}
