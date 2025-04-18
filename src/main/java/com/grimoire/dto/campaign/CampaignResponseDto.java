package com.grimoire.dto.campaign;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CampaignResponseDto {
    @Schema(example = "214")
    @JsonProperty("id")
    private Long id;

    @Schema(example = "2147483647")
    @JsonProperty("id_mestre")
    private Long idMaster;

    @Schema(example = "A Maldição do Dragão Sombrio")
    @JsonProperty("titulo")
    private String title;

    @Schema(example = "Uma antiga profecia alerta sobre o retorno do Dragão Sombrio. Apenas um grupo de aventureiros destemidos pode impedir a destruição do reino.")
    @JsonProperty("descricao")
    private String description;

    @Schema(example = "1024")
    @JsonProperty("id_sistema")
    private Long idSystem;

    @Schema(example = "67caef63dd0a8f4d0c0ad837")
    @JsonProperty("id_foto")
    private String idPicture;
}