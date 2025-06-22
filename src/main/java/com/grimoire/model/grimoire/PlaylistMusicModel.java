package com.grimoire.model.grimoire;

import com.grimoire.dto.playlist.PlaylistMusicResponseDto;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
@Table(name = "MUSICAS_PLAYLIST")
@EqualsAndHashCode
public class PlaylistMusicModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_PLAYLIST", nullable = false)
    private PlaylistModel playlist;

    @Column(name = "NOME")
    private String name;

    @Column(name = "LINK")
    private String link;

    public PlaylistMusicResponseDto toDto() {

        return PlaylistMusicResponseDto.builder()
                .id(this.id)
                .idCampaign(this.playlist.getCampaign().getId())
                .idCampaignMaster(this.playlist.getCampaign().getOwner().getId())
                .idPlaylist(this.playlist.getId())
                .name(this.name)
                .link(this.link)
                .build();
    }
}
