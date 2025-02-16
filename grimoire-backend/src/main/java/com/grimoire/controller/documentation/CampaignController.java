package com.grimoire.controller.documentation;

import com.grimoire.dto.campaign.CampaignCreateRequestDto;
import com.grimoire.dto.campaign.CampaignPostRequestDto;
import com.grimoire.dto.campaign.CampaignResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Campanha", description = "Serviço de Campanha")
public interface CampaignController {
    @Operation(description = "Registrar campanha", summary = "Registrar nova campanha no sistema")
    ResponseEntity<String> createCampaign(@Validated @RequestBody CampaignCreateRequestDto campaignDto);

    @Operation(description = "Atualizar campanha", summary = "Atualizar campanha no sistema. Deixe o campo em branco para mantê-lo.")
    ResponseEntity<String> updateCampaign(
            @Validated @RequestBody() CampaignPostRequestDto campaignDto,
            Authentication authentication);

    @Operation(description = "Deletar campanha", summary = "Remover campanha do sistema.")
    ResponseEntity<String> deleteCampaign(Authentication authentication);

    @Operation(description = "Ler campanha", summary = "Pegar informações da campanha no sistema.")
    ResponseEntity<CampaignResponseDto> getCampaign(Authentication authentication);
}

