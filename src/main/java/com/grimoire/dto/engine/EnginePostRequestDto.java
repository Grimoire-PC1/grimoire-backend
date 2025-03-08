package com.grimoire.dto.engine;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnginePostRequestDto {
    @Schema(defaultValue = "nome")
    @JsonProperty("novo_nome")
    private String name;

    @Schema(defaultValue = "descricao")
    @JsonProperty("nova_descricao")
    private String description;

    @Schema(defaultValue = "url")
    @JsonProperty("id_nova_foto")
    private String pictureId;
}
