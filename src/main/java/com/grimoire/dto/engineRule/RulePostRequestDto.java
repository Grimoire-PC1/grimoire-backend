package com.grimoire.dto.engineRule;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RulePostRequestDto {

    @Schema(defaultValue = "titulo")
    @JsonProperty("titulo")
    @NotNull
    private String title;

    @Schema(defaultValue = "descricao")
    @JsonProperty("descricao")
    @NotNull
    private String description;

}
