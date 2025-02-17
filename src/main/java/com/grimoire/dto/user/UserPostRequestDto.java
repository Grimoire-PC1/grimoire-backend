package com.grimoire.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserPostRequestDto {
    @Schema(defaultValue = "senha")
    @JsonProperty("nova_senha")
    @NotNull
    private String password;

    @Schema(defaultValue = "email")
    @JsonProperty("novo_email")
    @NotNull
    private String email;

    @Schema(defaultValue = "nome")
    @JsonProperty("novo_nome")
    @NotNull
    private String name;

    @Schema(defaultValue = "url")
    @JsonProperty("nova_foto_url")
    @NotNull
    private String pictureUrl;
}
