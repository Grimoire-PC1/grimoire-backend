package com.grimoire.dto.engine;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EngineCreateRequestDto {

    @JsonProperty("nome")
    @NotBlank
    private String name;

    @JsonProperty("descricao")
    @NotBlank
    private String description;

    @JsonProperty("foto_url")
    private String pictureUrl;

    @JsonProperty("tipo_sistema")
    @NotBlank
    private String typeSys;
}
