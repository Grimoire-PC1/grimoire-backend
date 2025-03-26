package com.grimoire.model.grimoire.typeTables;

import com.grimoire.dto.campaignPackage.CampaignFileTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
@Table(name = "TIPO_ARQUIVO")
@EqualsAndHashCode
public class FileTypeModel {
    @Id
    private Long id;

    @Column(name = "DESCRICAO")
    private String description;

    public FileTypeModel(CampaignFileTypeEnum typeEnum) {
        this.id = typeEnum.getId();
        this.description = typeEnum.getDescription();
    }
}
