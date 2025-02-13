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
    @Schema(example = "2147483647")
    @JsonProperty("idMestre")
    private long idMaster;

    @Schema(example = "A Maldição do Dragão Sombrio")
    @JsonProperty("titulo")
    private String title;

    @Schema(example = "Uma antiga profecia alerta sobre o retorno do Dragão Sombrio. Apenas um grupo de aventureiros destemidos pode impedir a destruição do reino.")
    @JsonProperty("descricao")
    private String description;

    @Schema(example = "1024")
    @JsonProperty("idSistema")
    private long idSystem;

    @Schema(example = "https://example.com/images/campaign-dragon-curse.jpg")
    @JsonProperty("foto_url")
    private String pictureUrl;

}