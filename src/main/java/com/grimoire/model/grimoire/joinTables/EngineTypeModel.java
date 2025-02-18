package com.grimoire.model.grimoire.joinTables;

import com.grimoire.dto.engine.EngineTypeEnum;
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
@Table(name = "TIPO_SISTEMA")
@EqualsAndHashCode
public class EngineTypeModel {
    @Id
    private Long id;

    @Column(name = "DESCRICAO")
    private String description;

    public EngineTypeModel(EngineTypeEnum typeEnum) {
        this.id = typeEnum.getId();
        this.description = typeEnum.getDescription();
    }

}
