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
    @Schema(example = "424242424242424L")
    @JsonProperty("usuario")
    private Long idUser;

    @Schema(example = "Dungeons & Dragons")
    @JsonProperty("nome")
    private String name;

    @Schema(example = "Magical Medieval Fantasy")
    @JsonProperty("descricao")
    private String description;

    @Schema(example = "url")
    @JsonProperty("foto_url")
    private String pictureUrl;

    @Schema(example = "Público")
    @JsonProperty("tipo_sistema")
    private String typeSys;
}
