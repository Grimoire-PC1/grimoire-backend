package com.grimoire.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequestDto {
    @JsonProperty("login")
    @NotBlank
    private String username;

    @JsonProperty("senha")
    @NotBlank
    private String password;

    @JsonProperty("email")
    @NotBlank
    private String email;

    @JsonProperty("nome")
    @NotBlank
    private String name;

    @JsonProperty("id_foto")
    private Long pictureID;
}
