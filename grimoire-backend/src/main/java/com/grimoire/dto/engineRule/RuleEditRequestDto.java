package com.grimoire.dto.engineRule;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Builder
@Data
public class RuleEditRequestDto {

    @Schema(defaultValue = "title")
    @JsonProperty("title")
    @NotNull
    private String title;

    @Schema(defaultValue = "description")
    @JsonProperty("descricao")
    @NotNull
    private String description;


    @Schema(defaultValue = "tipo_regra")
    @JsonProperty("tipo_regra")
    @NotNull
    private String type;
}
