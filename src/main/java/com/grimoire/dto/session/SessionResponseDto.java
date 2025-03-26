package com.grimoire.dto.session;

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
public class SessionResponseDto {
    @Schema(example = "214")
    @JsonProperty("id")
    private Long id;

    @Schema(example = "214")
    @JsonProperty("id_campanha")
    private Long idCampaign;

    @Schema(example = "214")
    @JsonProperty("id_campanha_mestre")
    private Long idCampaignMaster;

    @Schema(example = "")
    @JsonProperty("titulo")
    private String title;

    @Schema(example = "")
    @JsonProperty("data")
    private String date;

    @Schema(example = "descricao")
    @JsonProperty("descricao")
    private String description;

    @Schema(example = "FUTURA")
    @JsonProperty("tipo_sessao")
    private String typeSys;

    @Schema(example = "true")
    @JsonProperty("fixada")
    private Boolean fixed;
}
