package com.grimoire.dto.campaignPackage;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampaignFileCreateRequestDto {
    @JsonProperty("novo_id_pacote_pai")
    private Long idParentPackage;

    @JsonProperty("novo_nome")
    private String name;

    @JsonProperty("novo_conteudo")
    private String content;
}
