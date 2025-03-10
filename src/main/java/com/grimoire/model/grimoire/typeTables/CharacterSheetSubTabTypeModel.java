package com.grimoire.model.grimoire.typeTables;

import com.grimoire.dto.characterSheet.CharacterSheetSubTabTypeEnum;
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
@Table(name = "TIPO_SUB_ABA_FICHA")
@EqualsAndHashCode
public class CharacterSheetSubTabTypeModel {
    @Id
    private Long id;

    @Column(name = "DESCRICAO")
    private String description;

    public CharacterSheetSubTabTypeModel(CharacterSheetSubTabTypeEnum typeEnum) {
        this.id = typeEnum.getId();
        this.description = typeEnum.getDescription();
    }
}
