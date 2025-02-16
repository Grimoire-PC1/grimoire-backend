package com.grimoire.dto.engine;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EngineEditRequestDto {
    @Schema(defaultValue = "nome")
    @JsonProperty("novo_nome")
    @NotNull
    private String name;

    @Schema(defaultValue = "descricao")
    @JsonProperty("nova_descricao")
    @NotNull
    private String description;

    @Schema(defaultValue = "url")
    @JsonProperty("nova_foto_url")
    @NotNull
    private String pictureUrl;
}
