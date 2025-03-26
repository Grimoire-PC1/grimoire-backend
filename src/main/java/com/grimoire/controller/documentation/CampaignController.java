package com.grimoire.controller.documentation;

import com.grimoire.dto.campaign.CampaignCreateRequestDto;
import com.grimoire.dto.campaign.CampaignPostRequestDto;
import com.grimoire.dto.campaign.CampaignResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;

@Tag(name = "Campanha", description = "Serviço de Campanha")
public interface CampaignController {
    @Operation(description = "Registrar campanha", summary = "Registrar nova campanha no sistema")
    ResponseEntity<CampaignResponseDto> createCampaign(
            @Validated @RequestBody CampaignCreateRequestDto campaignDto,
            Authentication authentication);

    @Operation(description = "Atualizar campanha", summary = "Atualizar campanha no sistema. Deixe o campo vazio para mantê-lo.")
    ResponseEntity<CampaignResponseDto> updateCampaign(
            @Parameter(
                    name = "id_campanha",
                    description = "ID da Campanha de RPG.",
                    required = true
            )
            Long idCampaign,
            @Validated @RequestBody() CampaignPostRequestDto campaignDto,
            Authentication authentication);

    @Operation(description = "Deletar campanha", summary = "Remover campanha do sistema.")
    ResponseEntity<String> deleteCampaign(
            @Parameter(
                    name = "id_campanha",
                    description = "ID da Campanha de RPG.",
                    required = true
            )
            Long idCampaign,
            Authentication authentication);

    @Operation(description = "Pegar campanhas do usuário", summary = "Pegar informações das campanhas no sistema.")
    ResponseEntity<Collection<CampaignResponseDto>> getUserCampaigns(
            @Parameter(
                    name = "id_campanha",
                    description = "ID da Campanha de RPG."
            )
            Long idCampaign,
            Authentication authentication);

    @Operation(description = "Pegar campanhas que o usuário participa", summary = "Pegar informações das campanhas que o usuário participa no sistema.")
    ResponseEntity<Collection<CampaignResponseDto>> getUserParticipatingCampaigns(
            Authentication authentication);
}

