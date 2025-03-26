package com.grimoire.model.grimoire;

import com.grimoire.dto.campaignPackage.CampaignFileResponseDto;
import com.grimoire.model.grimoire.typeTables.FileTypeModel;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
@Table(name = "ARQUIVOS")
@EqualsAndHashCode
public class CampaignFileModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_PACOTE", nullable = false)
    private CampaignPackageModel campaignPackage;

    @ManyToOne
    @JoinColumn(name="ID_TIPO_ARQUIVO", nullable = false)
    private FileTypeModel fileType;

    @Column(name = "NOME")
    private String name;

    @Column(name = "TEXTO")
    private String text;

    @Column(name = "ID_IMAGEM")
    private String idPicture;

    @ManyToOne
    @JoinColumn(name = "ID_ITEM")
    private ItemModel item;

    @ManyToOne
    @JoinColumn(name = "ID_PERSONAGEM")
    private CharacterModel character;

    public CampaignFileResponseDto toDto() {
        CampaignFileResponseDto dto = CampaignFileResponseDto.builder()
                .id(this.id)
                .idPackage(this.campaignPackage.getId())
                .type(this.fileType.getDescription())
                .name(this.name)
                .build();
        if (this.fileType.getId() == 1L) {
            dto.setContent(this.text);
        } else if (this.fileType.getId() == 2L) {
            dto.setContent(this.idPicture);
        } else if (this.fileType.getId() == 3L) {
            dto.setContent(String.valueOf(this.item.getId()));
        } else if (this.fileType.getId() == 4L) {
            dto.setContent(String.valueOf(this.character.getId()));
        }
        return dto;
    }

}
