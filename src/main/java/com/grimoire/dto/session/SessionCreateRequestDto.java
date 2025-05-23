package com.grimoire.dto.session;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionCreateRequestDto {
    @Schema(example = "")
    @JsonProperty("titulo")
    private String title;

    @Schema(example = "")
    @JsonProperty("data")
    private String date;

    @Schema(example = "descricao")
    @JsonProperty("descricao")
    private String description;

    @Schema(example = "true")
    @JsonProperty("fixada")
    private Boolean fixed;
}
