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
public class CampaignPackageResponseDto {
    @Schema(example = "214")
    @JsonProperty("id")
    private Long id;

    @Schema(example = "2141")
    @JsonProperty("id_usuario")
    private Long idOwner;

    @Schema(example = "2142")
    @JsonProperty("id_campanha")
    private Long idCampaign;

    @Schema(example = "2143")
    @JsonProperty("id_campanha_mestre")
    private Long idCampaignMaster;

    @Schema(example = "2144")
    @JsonProperty("id_pacote_pai")
    private Long idParentPackage;

    @Schema(example = "Pacote")
    @JsonProperty("nome")
    private String name;

    @Schema(example = "true")
    @JsonProperty("publica")
    private Boolean isPublic;
}