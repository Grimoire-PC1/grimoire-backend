package com.grimoire.dto.campaign;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CampaignCreateRequestDto {
    @JsonProperty("idMestre")
    @NotBlank
    private Long idMaster;

    @JsonProperty("titulo")
    @NotBlank
    private String title;

    @JsonProperty("descricao")
    @NotBlank
    private String description;

    @JsonProperty("idSistema")
    @NotBlank
    private Long idSystem;

    @JsonProperty("foto_url")
    @NotBlank
    private String pictureUrl;
}
