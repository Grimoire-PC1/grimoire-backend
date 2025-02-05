package com.grimoire.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserCreateRequestDto {
    @JsonProperty("login")
    @NotBlank
    private String username;

    @JsonProperty("senha")
    @NotBlank
    private String password;

    @JsonProperty("gmail")
    @NotBlank
    private String gmail;

    @JsonProperty("nome")
    @NotBlank
    private String name;

    @JsonProperty("foto_url")
    private String pictureUrl;
}
