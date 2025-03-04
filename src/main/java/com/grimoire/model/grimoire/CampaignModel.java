package com.grimoire.model.grimoire;

import com.grimoire.dto.campaign.CampaignResponseDto;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
@Table(name = "CAMPANHAS")
@EqualsAndHashCode
public class CampaignModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_CRIADOR", nullable = false)
    private UserModel owner;

    @Column(name = "TITULO")
    private String title;

    @Column(name = "DESCRICAO")
    private String description;

    @ManyToOne
    @JoinColumn(name = "ID_SISTEMA")
    private EngineModel engine;

    @Column(name = "ID_FOTO")
    private String idPicture;

    public CampaignResponseDto toDto() {
        return CampaignResponseDto.builder()
                .id(this.id)
                .idMaster(this.owner.getId())
                .title(this.title)
                .description(this.description)
                .idSystem(this.engine == null ? null : this.engine.getId())
                .idPicture(this.idPicture)
                .build();
    }
}

