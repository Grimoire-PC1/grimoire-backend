package com.grimoire.dto.campaignPackage;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CampaignFileResponseDto {
    @Schema(example = "214")
    @JsonProperty("id")
    private Long id;

    @Schema(example = "2147483647")
    @JsonProperty("id_pacote_pai")
    private Long idPackage;

    @Schema(example = "TEXTO")
    @JsonProperty("tipo")
    private String type;

    @Schema(example = "Arquivo")
    @JsonProperty("nome")
    private String name;

    @Schema(example = "Uma antiga profecia alerta sobre o retorno do Dragão Sombrio...")
    @JsonProperty("conteudo")
    private String content;
}