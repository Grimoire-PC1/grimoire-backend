package com.grimoire.dto.campaign;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CampaignCreateRequestDto {
    @JsonProperty("idMestre")
    @NotNull
    private Long idMaster;

    @JsonProperty("titulo")
    @NotBlank
    private String title;

    @JsonProperty("descricao")
    @NotBlank
    private String description;

    @JsonProperty("idSistema")
    @NotNull
    private Long idSystem;

    @JsonProperty("foto_url")
    private String pictureUrl;
}
