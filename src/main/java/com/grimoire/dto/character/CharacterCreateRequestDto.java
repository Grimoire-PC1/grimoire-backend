package com.grimoire.dto.character;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CharacterCreateRequestDto {
    @JsonProperty("nome")
    private String name;

    @JsonProperty("id_foto")
    @NotNull
    private Long idPicture;
}
