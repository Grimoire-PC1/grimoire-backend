package com.grimoire.dto.engineRule;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuleCreateRequestDto {
    @JsonProperty("id_sistema")
    @NotNull
    private Long idSys;

    @JsonProperty("titulo")
    private String title;

    @JsonProperty("descricao")
    private String description;
}
