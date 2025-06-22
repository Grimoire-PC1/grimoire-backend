package com.grimoire.model.grimoire;

import com.grimoire.dto.playlist.PlaylistResponseDto;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
@Table(name = "PLAYLISTS")
@EqualsAndHashCode
public class PlaylistModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_CAMPANHA", nullable = false)
    private CampaignModel campaign;

    @Column(name = "NOME")
    private String name;

    public PlaylistResponseDto toDto() {

        return PlaylistResponseDto.builder()
                .id(this.id)
                .idCampaign(this.campaign.getId())
                .idCampaignMaster(this.campaign.getOwner().getId())
                .name(this.name)
                .build();
    }

}
