package com.grimoire.model.grimoire.embeddedId;

import com.grimoire.model.grimoire.CampaignModel;
import com.grimoire.model.grimoire.EngineModel;
import com.grimoire.model.grimoire.UserModel;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode
public class ParticipantModelId implements Serializable {

    @ManyToOne
    @JoinColumn(name="ID_USUARIO")
    private UserModel userModel;

    @ManyToOne
    @JoinColumn(name="ID_CAMPANHA")
    private CampaignModel campaignModel;
}
