package com.grimoire.dto.engineRule;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
public class RuleCreateRequestDto {
    @JsonProperty("id_usuario")
    @NotBlank
    private Long idUser;

    @JsonProperty("id_sistema")
    @NotBlank
    private Long idSys;

    @JsonProperty("title")
    @NotBlank
    private String title;

    @JsonProperty("descricao")
    @NotBlank
    private String description;

    @JsonProperty("tipo_regra")
    @NotBlank
    private String type;
}
