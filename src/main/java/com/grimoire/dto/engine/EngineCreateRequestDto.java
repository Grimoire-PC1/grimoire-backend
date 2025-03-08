package com.grimoire.dto.engine;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EngineCreateRequestDto {

    @JsonProperty("nome")
    @NotBlank
    private String name;

    @JsonProperty("descricao")
    @NotBlank
    private String description;

    @JsonProperty("id_foto")
    private String pictureId;
}
