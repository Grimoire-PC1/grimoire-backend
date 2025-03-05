package com.grimoire.model.grimoire;

import com.grimoire.dto.character.CharacterResponseDto;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
@Table(name = "PERSONAGENS")
@EqualsAndHashCode
public class CharacterModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private UserModel user;

    @ManyToOne
    @JoinColumn(name = "ID_CAMPANHA", nullable = false)
    private CampaignModel campaign;

    @Column(name = "NOME")
    private String name;

    @Column(name = "ID_FOTO")
    private Long idPicture;

    public CharacterResponseDto toDto() {
        return CharacterResponseDto.builder()
                .id(this.id)
                .idUser(this.user.getId())
                .idCampaign(this.campaign.getId())
                .idCampaignMaster(this.campaign.getOwner().getId())
                .name(this.name)
                .idPicture(this.idPicture)
                .build();
    }
}

