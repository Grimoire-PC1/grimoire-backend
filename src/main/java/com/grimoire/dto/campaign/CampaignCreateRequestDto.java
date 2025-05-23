package com.grimoire.dto.campaign;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampaignCreateRequestDto {
    @JsonProperty("titulo")
    private String title;

    @JsonProperty("descricao")
    private String description;

    @JsonProperty("id_sistema")
    private Long idSystem;

    @JsonProperty("id_foto")
    private String idPicture;
}
