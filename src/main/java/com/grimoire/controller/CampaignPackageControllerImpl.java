package com.grimoire.controller;

import com.grimoire.controller.documentation.CampaignPackageController;
import com.grimoire.dto.campaignPackage.*;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    @PostMapping("/register")
    public ResponseEntity<CampaignPackageResponseDto> create(
            @RequestParam(name = "id_campanha", required = true) Long campaignId,
            @RequestParam(name = "publica", required = true) Boolean isPublic,
            @Validated @RequestBody CampaignPackageCreateRequestDto campaignPackageDto,
            Authentication authentication) {
        return null;
    }

    @Override
    @PutMapping("/update")
    public ResponseEntity<CampaignPackageResponseDto> update(
            @RequestParam(name = "id_pacote", required = true) Long packageId,
            @Validated @RequestBody CampaignPackagePostRequestDto campaignPackageDto,
            Authentication authentication) {
        return null;
    }

    @Override
    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(
            @RequestParam(name = "id_pacote", required = true) Long packageId,
            Authentication authentication) {
        return null;
    }

    @Override
    @GetMapping("/get")
    public ResponseEntity<Collection<CampaignPackageResponseDto>> getByCampaign(
            @RequestParam(name = "id_campanha", required = true) Long campaignId,
            @RequestParam(name = "id_pacote_pai", required = false) Long parentPackageId,
            Authentication authentication) {
        return null;
    }

    @Override
    @PostMapping("/file/register")
    public ResponseEntity<CampaignFileResponseDto> createFile(
            @RequestParam(name = "id_campanha", required = true) Long campaignId,
            @RequestParam(name = "id_pacote_pai", required = true) Long packageId,
            @RequestParam(name = "tipo_arquivo", required = true) CampaignFileTypeEnum campaignFileTypeEnum,
            @Validated @RequestBody CampaignFileCreateRequestDto campaignFileDto,
            Authentication authentication) {
        return null;
    }

    @Override
    @PutMapping("/file/update")
    public ResponseEntity<CampaignFileResponseDto> updateFile(
            @RequestParam(name = "id_arquivo", required = true) Long campaignFileId,
            @RequestParam(name = "novo_tipo_arquivo", required = false) CampaignFileTypeEnum campaignFileTypeEnum,
            @Validated @RequestBody CampaignFilePostRequestDto campaignFileDto,
            Authentication authentication) {
        return null;
    }

    @Override
    @DeleteMapping("/file/delete")
    public ResponseEntity<String> deleteFile(
            @RequestParam(name = "id_arquivo", required = true) Long campaignFileId,
            Authentication authentication) {
        return null;
    }

    @Override
    @GetMapping("/file/get")
    public ResponseEntity<Collection<CampaignFileResponseDto>> getFileByCampaign(
            @RequestParam(name = "id_campanha", required = true) Long campaignId,
            @RequestParam(name = "id_pacote_pai", required = false) Long parentPackageId,
            Authentication authentication) {
        return null;
    }
}
