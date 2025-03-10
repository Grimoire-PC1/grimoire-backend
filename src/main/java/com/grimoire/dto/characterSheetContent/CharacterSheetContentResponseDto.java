package com.grimoire.dto.characterSheetContent;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CharacterSheetContentResponseDto {
    @Schema(example = "42")
    @JsonProperty("id")
    private Long id;

    @Schema(example = "42")
    @JsonProperty("id_personagem")
    private Long idCharacter;

    @Schema(example = "214")
    @JsonProperty("id_sub_aba_ficha")
    private Long idCharacterTab;

    @Schema(example = "Nome")
    @JsonProperty("nome_sub_aba_ficha")
    private String name;

    @Schema(example = "TEXTO")
    @JsonProperty("tipo_sub_aba_ficha")
    private String type;

    @JsonProperty("conteudo")
    private List<String> content;
}
