package com.grimoire.model.grimoire;

import com.grimoire.dto.session.SessionResponseDto;
import com.grimoire.model.grimoire.joinTables.EngineTypeModel;
import com.grimoire.model.grimoire.joinTables.SessionTypeModel;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
@Table(name = "SESSOES")
@EqualsAndHashCode
public class SessionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_CAMPANHA", nullable = false)
    private CampaignModel campaign;

    @Column(name = "TITULO")
    private String title;

    @Column(name = "DATA")
    private String date;

    @Column(name = "DESCRICAO")
    private String description;

    @ManyToOne
    @JoinColumn(name="ID_TIPO_SESSAO")
    private SessionTypeModel sessionType;

    @Column(name = "FIXADA")
    private Boolean fixed;

    public SessionResponseDto toDto() {
        return SessionResponseDto.builder()
                .id(this.id)
                .idCampaign(this.campaign.getId())
                .idCampaignMaster(this.campaign.getOwner().getId())
                .date(this.date)
                .title(this.title)
                .description(this.description)
                .typeSys(this.sessionType.getDescription())
                .fixed(this.fixed)
                .build();
    }
}
