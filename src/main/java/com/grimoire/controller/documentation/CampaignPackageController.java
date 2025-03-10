package com.grimoire.controller.documentation;

import com.grimoire.dto.campaignPackage.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

@Tag(name = "Pacote", description = "Serviço de Pacotes de Campanha")
public interface CampaignPackageController {
    @Operation(description = "Registrar Pacote", summary = "Registrar novo Pacote de Campanha de RPG no Grimoire")
    ResponseEntity<CampaignPackageResponseDto> create(
            @Parameter(
                    name = "id_campanha",
                    description = "ID da Campanha de RPG.",
                    required = true
            )
            Long campaignId,
            @Parameter(
                    name = "publica",
                    description = "Define se Pasta é Pública.",
                    required = true
            )
            Boolean isPublic,
            @Validated @RequestBody CampaignPackageCreateRequestDto campaignPackageDto,
            Authentication authentication);

    @Operation(description = "Registrar Pacote para Personagem", summary = "Registrar novo Pacote com Arquivo de Personagem no Grimoire")
    ResponseEntity<CampaignPackageResponseDto> createByCharacter(
            @Parameter(
                    name = "id_personagem",
                    description = "ID do Personagem de RPG.",
                    required = true
            )
            Long characterId,
            @Validated @RequestBody CampaignPackageCreateRequestDto campaignPackageDto,
            Authentication authentication);

    @Operation(description = "Atualizar Pacote", summary = "Atualizar Pacote de Campanha de RPG no Grimoire")
    ResponseEntity<CampaignPackageResponseDto> update(
            @Parameter(
                    name = "id_pacote",
                    description = "ID do Pacote de RPG.",
                    required = true
            )
            Long packageId,
            @Validated @RequestBody CampaignPackagePostRequestDto campaignPackageDto,
            Authentication authentication);

    @Operation(description = "Remover Pacote", summary = "Remover Pacote de Campanha de RPG no Grimoire.")
    ResponseEntity<String> delete(
            @Parameter(
                    name = "id_pacote",
                    description = "ID do Pacote de RPG.",
                    required = true
            )
            Long packageId,
            Authentication authentication);

    @Operation(description = "Pegar Pacotes", summary = "Pegar informações de Pacotes de Campanha de RPG no Grimoire.")
    ResponseEntity<Collection<CampaignPackageResponseDto>> getByCampaign(
            @Parameter(
                    name = "id_campanha",
                    description = "ID da Campanha de RPG.",
                    required = true
            )
            Long campaignId,
            @Parameter(
                    name = "id_pacote_pai",
                    description = "ID do Pacote Pai.",
                    required = false
            )
            Long parentPackageId,
            Authentication authentication);

    @Operation(description = "Registrar Arquivo", summary = "Registrar novo Arquivo de Campanha de RPG no Grimoire")
    ResponseEntity<CampaignFileResponseDto> createFile(
            @Parameter(
                    name = "id_pacote",
                    description = "ID do Pacote de RPG.",
                    required = true
            )
            Long packageId,
            @Parameter(
                    name = "tipo_arquivo",
                    description = "Tipo de Arquivo.",
                    required = true
            )
            CampaignFileTypeEnum campaignFileTypeEnum,
            @Validated @RequestBody CampaignFileCreateRequestDto campaignFileDto,
            Authentication authentication);

    @Operation(description = "Atualizar Arquivo", summary = "Atualizar Arquivo de Campanha de RPG no Grimoire")
    ResponseEntity<CampaignFileResponseDto> updateFile(
            @Parameter(
                    name = "id_arquivo",
                    description = "ID do Arquivo de RPG.",
                    required = true
            )
            Long campaignFileId,
            @Validated @RequestBody CampaignFilePostRequestDto campaignFileDto,
            Authentication authentication);

    @Operation(description = "Remover Arquivo", summary = "Remover Arquivo de Campanha de RPG no Grimoire.")
    ResponseEntity<String> deleteFile(
            @Parameter(
                    name = "id_arquivo",
                    description = "ID do Arquivo de RPG.",
                    required = true
            )
            Long campaignFileId,
            Authentication authentication);

    @Operation(description = "Pegar Arquivos", summary = "Pegar Arquivos de Campanha de RPG no Grimoire.")
    ResponseEntity<Collection<CampaignFileResponseDto>> getFileByCampaign(
            @Parameter(
                    name = "id_campanha",
                    description = "ID da Campanha de RPG.",
                    required = true
            )
            Long campaignId,
            @Parameter(
                    name = "id_pacote_pai",
                    description = "ID do Pacote Pai.",
                    required = false
            )
            Long parentPackageId,
            Authentication authentication);
}
