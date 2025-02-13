package com.grimoire.controller;

import com.grimoire.controller.documentation.CampaignController;
import com.grimoire.dto.campaign.CampaignCreateRequestDto;
import com.grimoire.dto.campaign.CampaignPostRequestDto;
import com.grimoire.dto.campaign.CampaignResponseDto;
import com.grimoire.service.service.AuthService;
import com.grimoire.service.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/campaign")
public class CampaignControllerImpl implements CampaignController {
    private final CampaignService campaignService;
    @Autowired
    public CampaignControllerImpl(CampaignService campaignService, AuthService authService) {
        this.campaignService = campaignService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> createCampaign(@Validated @RequestBody CampaignCreateRequestDto campaignDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(campaignService.createCampaign(campaignDto));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateCampaign(
            @Validated @RequestBody CampaignPostRequestDto campaignDto,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(campaignService.postCampaign(authentication.getName(), campaignDto));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCampaign(
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(campaignService.deleteCampaign(authentication.getName()));
    }

    @GetMapping("/get")
    public ResponseEntity<CampaignResponseDto> getCampaign(
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(campaignService.getCampaign(authentication.getName()));
    }
}
