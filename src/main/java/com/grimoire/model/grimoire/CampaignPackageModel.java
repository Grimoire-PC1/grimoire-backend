package com.grimoire.model.grimoire;

import com.grimoire.dto.campaign.CampaignResponseDto;
import com.grimoire.dto.campaignPackage.CampaignPackageResponseDto;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
@Table(name = "PACOTES")
@EqualsAndHashCode
public class CampaignPackageModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="ID_USUARIO", nullable = false)
    private UserModel userModel;

    @ManyToOne
    @JoinColumn(name = "ID_CAMPANHA", nullable = false)
    private CampaignModel campaign;

    @ManyToOne
    @JoinColumn(name = "ID_PACOTE_PAI")
    private CampaignPackageModel parentPackage;

    @Column(name = "NOME")
    private String name;

    @Column(name = "PUBLICA")
    private Boolean isPublic;

    public CampaignPackageResponseDto toDto() {
        return CampaignPackageResponseDto.builder()
                .id(this.id)
                .idOwner(this.userModel.getId())
                .idCampaign(this.campaign.getId())
                .idCampaignMaster(this.campaign.getOwner().getId())
                .idParentPackage(this.parentPackage == null ? null : this.parentPackage.getId())
                .name(this.name)
                .isPublic(this.isPublic)
                .build();
    }
}
