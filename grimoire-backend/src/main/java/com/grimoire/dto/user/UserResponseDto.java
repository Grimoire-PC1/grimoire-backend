package com.grimoire.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserResponseDto {
    @Schema(example = "Holux")
    @JsonProperty("login")
    private String username;

    @Schema(example = "evaldo.brito.junior@ccc.ufcg.edu.br")
    @JsonProperty("gmail")
    private String gmail;

    @Schema(example = "Evaldo Alves")
    @JsonProperty("nome")
    private String name;

    @Schema(example = "url")
    @JsonProperty("foto_url")
    private String pictureUrl;
}
