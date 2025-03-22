package com.grimoire.controller;

import com.grimoire.controller.documentation.CampaignPackageController;
import com.grimoire.dto.campaignPackage.*;
import com.grimoire.service.interfaces.CampaignPackageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/campaign-package")
@Validated
@CrossOrigin
@Slf4j
public class CampaignPackageControllerImpl implements CampaignPackageController {

    private final CampaignPackageService campaignPackageService;
    @Autowired
    public CampaignPackageControllerImpl(CampaignPackageService campaignPackageService) {
        this.campaignPackageService = campaignPackageService;
    }

    @Override
    @PostMapping("/register")
    public ResponseEntity<CampaignPackageResponseDto> create(
            @RequestParam(name = "id_campanha", required = true) Long campaignId,
            @RequestParam(name = "publica", required = true) Boolean isPublic,
            @Validated @RequestBody CampaignPackageCreateRequestDto campaignPackageDto,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                campaignPackageService.create(campaignId, isPublic, campaignPackageDto, authentication.getName()));
    }

    @Override
    @PostMapping("/register/character")
    public ResponseEntity<CampaignPackageResponseDto> createByCharacter(
            @RequestParam(name = "id_personagem", required = true) Long characterId,
            @Validated @RequestBody CampaignPackageCreateRequestDto campaignPackageDto,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                campaignPackageService.createByCharacter(characterId, campaignPackageDto, authentication.getName()));
    }

    @Override
    @PutMapping("/update")
    public ResponseEntity<CampaignPackageResponseDto> update(
            @RequestParam(name = "id_pacote", required = true) Long packageId,
            @Validated @RequestBody CampaignPackagePostRequestDto campaignPackageDto,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(
                campaignPackageService.post(packageId, campaignPackageDto, authentication.getName()));
    }

    @Override
    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(
            @RequestParam(name = "id_pacote", required = true) Long packageId,
            Authentication authentication) {

        return ResponseEntity.status(HttpStatus.OK).body(
                campaignPackageService.delete(packageId, authentication.getName()));
    }

    @Override
    @GetMapping("/get")
    public ResponseEntity<Collection<CampaignPackageResponseDto>> getByCampaign(
            @RequestParam(name = "id_campanha", required = true) Long campaignId,
            @RequestParam(name = "id_pacote_pai", required = false) Long parentPackageId,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(
                campaignPackageService.get(campaignId, parentPackageId, authentication.getName()));
    }

    @Override
    @PostMapping("/file/register")
    public ResponseEntity<CampaignFileResponseDto> createFile(
            @RequestParam(name = "id_pacote_pai", required = true) Long packageId,
            @RequestParam(name = "tipo_arquivo", required = true) CampaignFileTypeEnum campaignFileTypeEnum,
            @Validated @RequestBody CampaignFileCreateRequestDto campaignFileDto,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                campaignPackageService.createFile(packageId, campaignFileTypeEnum, campaignFileDto, authentication.getName()));
    }

    @Override
    @PutMapping("/file/update")
    public ResponseEntity<CampaignFileResponseDto> updateFile(
            @RequestParam(name = "id_arquivo", required = true) Long campaignFileId,
            @Validated @RequestBody CampaignFilePostRequestDto campaignFileDto,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(
                campaignPackageService.updateFile(campaignFileId, campaignFileDto, authentication.getName()));
    }

    @Override
    @DeleteMapping("/file/delete")
    public ResponseEntity<String> deleteFile(
            @RequestParam(name = "id_arquivo", required = true) Long campaignFileId,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(
                campaignPackageService.deleteFile(campaignFileId, authentication.getName()));
    }

    @Override
    @GetMapping("/file/get")
    public ResponseEntity<Collection<CampaignFileResponseDto>> getFileByCampaign(
            @RequestParam(name = "id_campanha", required = true) Long campaignId,
            @RequestParam(name = "id_pacote_pai", required = false) Long parentPackageId,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(
                campaignPackageService.getFile(campaignId, parentPackageId, authentication.getName()));
    }
}
