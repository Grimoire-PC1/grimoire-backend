package com.grimoire.dto.campaign;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CampaignPostRequestDto {
    @Schema(defaultValue = "titulo")
    @JsonProperty("novo_titulo")
    @NotNull
    private String title;

    @Schema(defaultValue = "descricao")
    @JsonProperty("nova_descricao")
    @NotNull
    private String description;

    @Schema(defaultValue = "5")
    @JsonProperty("id_novo_sistema")
    private Long idSystem;

    @Schema(defaultValue = "url")
    @JsonProperty("nova_foto_url")
    @NotNull
    private String pictureUrl;
}
