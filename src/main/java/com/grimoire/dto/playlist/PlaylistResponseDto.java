package com.grimoire.dto.playlist;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistResponseDto {
    @Schema(example = "42")
    @JsonProperty("id")
    private Long id;

    @Schema(example = "214")
    @JsonProperty("id_campanha")
    private Long idCampaign;

    @Schema(example = "214")
    @JsonProperty("id_campanha_mestre")
    private Long idCampaignMaster;

    @Schema(example = "Nome")
    @JsonProperty("nome")
    private String name;
}
