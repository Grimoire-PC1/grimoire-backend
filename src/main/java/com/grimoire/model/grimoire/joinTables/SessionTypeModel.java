package com.grimoire.model.grimoire.joinTables;

import com.grimoire.dto.engine.EngineTypeEnum;
import com.grimoire.dto.session.SessionTypeEnum;
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
@Table(name = "TIPO_SESSAO")
@EqualsAndHashCode
public class SessionTypeModel {
    @Id
    private Long id;

    @Column(name = "DESCRICAO")
    private String description;

    public SessionTypeModel(SessionTypeEnum typeEnum) {
        this.id = typeEnum.getId();
        this.description = typeEnum.getDescription();
    }

}
