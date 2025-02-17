package com.grimoire.dto.campaign;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CampaignPostRequestDto {
    @Schema(defaultValue = "idMestre")
    @JsonProperty("id_novo_mestre")
    @NotNull
    private long idMaster;

    @Schema(defaultValue = "titulo")
    @JsonProperty("novo_titulo")
    @NotNull
    private String title;

    @Schema(defaultValue = "descricao")
    @JsonProperty("nova_descricao")
    @NotNull
    private String description;

    @Schema(defaultValue = "idSistema")
    @JsonProperty("id_novo_sistema")
    @NotNull
    private long idSystem;

    @Schema(defaultValue = "url")
    @JsonProperty("nova_foto_url")
    @NotNull
    private String pictureUrl;
}
