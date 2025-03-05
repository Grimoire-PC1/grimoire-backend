package com.grimoire.dto.mechanic;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MechanicPostRequestDto {
    @JsonProperty("nome")
    private String name;

    @JsonProperty("descricao")
    private String description;

    @JsonProperty("acoes")
    private List<String> actions;

    @JsonProperty("efeitos")
    private List<String> effects;
}
