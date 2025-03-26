package com.grimoire.dto.characterSheetTemplate;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CharacterSheetSubTabResponseDto {
    @Schema(example = "42")
    @JsonProperty("id")
    private Long id;

    @Schema(example = "214")
    @JsonProperty("id_aba_ficha")
    private Long idCharacterTab;

    @Schema(example = "214")
    @JsonProperty("id_sistema")
    private Long idEngine;

    @Schema(example = "214")
    @JsonProperty("id_sistema_criador")
    private Long idEngineOwner;

    @Schema(example = "Nome")
    @JsonProperty("nome")
    private String name;

    @Schema(example = "TEXTO")
    @JsonProperty("tipo_sub_aba_ficha")
    private String type;
}
