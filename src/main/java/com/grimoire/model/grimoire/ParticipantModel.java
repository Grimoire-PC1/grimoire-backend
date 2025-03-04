package com.grimoire.model.grimoire;

import com.grimoire.dto.participant.ParticipantResponseDto;
import com.grimoire.model.grimoire.embeddedId.ParticipantModelId;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
@Table(name = "PARTICIPANTES")
@EqualsAndHashCode
public class ParticipantModel {

    @EmbeddedId
    private ParticipantModelId modelId;

    public ParticipantResponseDto toDto() {
        return ParticipantResponseDto.builder()
                .idUser(this.modelId.getUserModel().getId())
                .idCampaign(this.modelId.getCampaignModel().getId())
                .userName(this.modelId.getUserModel().getName())
                .userPictureId(this.modelId.getUserModel().getPictureID())
                .build();
    }
}
