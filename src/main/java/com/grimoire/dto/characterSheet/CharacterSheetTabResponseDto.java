package com.grimoire.dto.characterSheet;

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
public class CharacterSheetTabResponseDto {
    @Schema(example = "42")
    @JsonProperty("id")
    private Long id;

    @Schema(example = "214")
    @JsonProperty("id_sistema")
    private Long idEngine;

    @Schema(example = "214")
    @JsonProperty("id_sistema_criador")
    private Long idEngineOwner;

    @Schema(example = "Habilidades")
    @JsonProperty("nome")
    private String name;
}
