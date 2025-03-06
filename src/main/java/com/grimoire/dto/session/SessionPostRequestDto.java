package com.grimoire.dto.session;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionPostRequestDto {
    @Schema(example = "")
    @JsonProperty("nova_data")
    private String date;

    @Schema(example = "descricao")
    @JsonProperty("nova_descricao")
    private String description;

    @Schema(example = "true")
    @JsonProperty("fixada")
    private boolean fixed;
}
