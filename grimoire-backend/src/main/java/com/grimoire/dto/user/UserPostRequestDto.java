package com.grimoire.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserPostRequestDto {
    @Schema(example = "Holux")
    @JsonProperty("login")
    private String username;

    @Schema(example = "senha123")
    @JsonProperty("senha")
    private String password;

    @Schema(example = "evaldo.brito.junior@ccc.ufcg.edu.br")
    @JsonProperty("gmail")
    private String gmail;

    @Schema(example = "url")
    @JsonProperty("foto_url")
    private String pictureUrl;
}
