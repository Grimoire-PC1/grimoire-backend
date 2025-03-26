package com.grimoire.dto.mechanic;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MechanicResponseDto {
    @Schema(example = "42")
    @JsonProperty("id")
    private Long id;

    @Schema(example = "214")
    @JsonProperty("id_sistema")
    private Long idEngine;

    @Schema(example = "214")
    @JsonProperty("id_sistema_criador")
    private Long idEngineOwner;

    @Schema(example = "Mecânica")
    @JsonProperty("nome")
    private String name;

    @Schema(example = "42")
    @JsonProperty("descricao")
    private String description;

    @Schema(example = "[acao]")
    @JsonProperty("acoes")
    private String actions;

    @Schema(example = "[efeito]")
    @JsonProperty("efeitos")
    private String effects;
}