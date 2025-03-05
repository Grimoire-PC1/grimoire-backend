package com.grimoire.dto.character;

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
public class CharacterResponseDto {
    @Schema(example = "214")
    @JsonProperty("id")
    private Long id;

    @Schema(example = "214")
    @JsonProperty("id_usuario")
    private Long idUser;

    @Schema(example = "214")
    @JsonProperty("id_campanha")
    private Long idCampaign;

    @Schema(example = "214")
    @JsonProperty("id_campanha_mestre")
    private Long idCampaignMaster;

    @Schema(example = "nome")
    @JsonProperty("nome")
    private String name;

    @Schema(example = "214")
    @JsonProperty("id_foto")
    private Long idPicture;
}