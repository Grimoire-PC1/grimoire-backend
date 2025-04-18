package com.grimoire.dto.character;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CharacterPostRequestDto {
    @JsonProperty("novo_nome")
    private String name;

    @JsonProperty("id_nova_foto")
    private String idPicture;
}
