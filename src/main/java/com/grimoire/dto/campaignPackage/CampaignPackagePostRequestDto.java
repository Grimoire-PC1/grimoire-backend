package com.grimoire.dto.campaignPackage;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CampaignPackagePostRequestDto {
    @JsonProperty("novo_id_pacote_pai")
    private Long idParentPackage;

    @JsonProperty("novo_nome")
    private String name;

    @JsonProperty("publica")
    private Boolean isPublic;
}
