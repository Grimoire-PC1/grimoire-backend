package com.grimoire.dto.engine;

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
public class EngineResponseDto {
    @Schema(example = "42L")
    @JsonProperty("id")
    private Long id;

    @Schema(example = "42424L")
    @JsonProperty("id_criador")
    private Long idUser;

    @Schema(example = "Dungeons & Dragons")
    @JsonProperty("nome")
    private String name;

    @Schema(example = "Magical Medieval Fantasy")
    @JsonProperty("descricao")
    private String description;

    @Schema(example = "67caef63dd0a8f4d0c0ad837")
    @JsonProperty("id_foto")
    private String pictureId;

    @Schema(example = "PUBLICO")
    @JsonProperty("tipo_sistema")
    private String typeSys;
}
