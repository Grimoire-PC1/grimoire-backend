package com.grimoire.dto.user;

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
public class UserResponseDto {
    @Schema(example = "214")
    @JsonProperty("id")
    private Long id;

    @Schema(example = "Holux")
    @JsonProperty("login")
    private String username;

    @Schema(example = "evaldo.brito.junior@ccc.ufcg.edu.br")
    @JsonProperty("email")
    private String email;

    @Schema(example = "Evaldo Alves")
    @JsonProperty("nome")
    private String name;

    @Schema(example = "67caef63dd0a8f4d0c0ad837")
    @JsonProperty("id_foto")
    private String pictureID;
}
