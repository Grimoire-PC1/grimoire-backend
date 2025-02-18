package com.grimoire.dto.participant;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ParticipantResponseDto {

    @Schema(example = "42")
    @JsonProperty("id_usuario")
    private Long idUser;

    @Schema(example = "42")
    @JsonProperty("id_campanha")
    private Long idCampaign;

    @Schema(example = "Evaldo Alves")
    @JsonProperty("nome_usuario")
    private String userName;

    @Schema(example = "url")
    @JsonProperty("foto_url_usuario")
    private String userPictureUrl;
}
