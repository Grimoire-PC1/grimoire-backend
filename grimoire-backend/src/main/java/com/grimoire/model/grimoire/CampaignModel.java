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

    @Column(name = "ID_MESTRE", unique = true, nullable = false)
    private Long idMaster;

    @Column(name = "TITULO", nullable = false)
    private String title;

    @Column(name = "DESCRICAO", nullable = false)
    private String description;

    @Column(name = "ID_SISTEMA", unique = true, nullable = false)
    private Long idSystem;

    @Column(name = "URL_FOTO")
    private String pictureUrl;

    public CampaignResponseDto toDto() {
        return CampaignResponseDto.builder()
                .idMaster(this.idMaster)
                .title(this.title)
                .description(this.description)
                .idSystem(this.idSystem)
                .pictureUrl(this.pictureUrl)
                .build();
    }

}

