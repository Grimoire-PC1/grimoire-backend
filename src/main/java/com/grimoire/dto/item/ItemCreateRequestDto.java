package com.grimoire.dto.item;

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
public class ItemCreateRequestDto {
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
