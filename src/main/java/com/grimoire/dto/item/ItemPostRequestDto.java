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
public class ItemPostRequestDto {
    @JsonProperty("novo_nome")
    private String name;

    @JsonProperty("nova_descricao")
    private String description;

    @JsonProperty("nova_qtd_dados")
    private Integer diceQuantity;

    @JsonProperty("novo_tipo_dado")
    private Integer diceType;

    @JsonProperty("novo_bonus_dado")
    private Integer diceBonus;

    @JsonProperty("novo_rolar_dado")
    private String rollDice;

    @JsonProperty("nova_quantidade")
    private Integer itemQuantity;
}
