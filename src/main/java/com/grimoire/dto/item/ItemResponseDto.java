package com.grimoire.dto.item;

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
public class ItemResponseDto {
    @Schema(example = "214")
    @JsonProperty("id")
    private Long id;

    @Schema(example = "214")
    @JsonProperty("id_campanha")
    private Long idCampaign;

    @Schema(example = "214")
    @JsonProperty("id_campanha_mestre")
    private Long idCampaignMaster;

    @JsonProperty("nome")
    private String name;

    @JsonProperty("descricao")
    private String description;

    @JsonProperty("qtd_dados")
    private Integer diceQuantity;

    @JsonProperty("tipo_dado")
    private Integer diceType;

    @JsonProperty("bonus_dado")
    private Integer diceBonus;

    @JsonProperty("rolar_dado")
    private String rollDice;

    @JsonProperty("quantidade")
    private Integer itemQuantity;
}
