package com.grimoire.dto.engineRule;

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
public class RuleResponseDto {
    @Schema(example = "42424L")
    @JsonProperty("id")
    private Long id;

    @Schema(example = "4242424242424L")
    @JsonProperty("id_sistema")
    private Long idSys;

    @Schema(example = "Ability Check")
    @JsonProperty("titulo")
    private String title;

    @Schema(example = "An ability check tests a " +
            "character’s or monster’s innate " +
            "talent and training in an effort to overcome a challenge.")
    @JsonProperty("descricao")
    private String description;
}
