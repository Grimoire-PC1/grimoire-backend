package com.grimoire.model.grimoire;

import com.grimoire.dto.engine.EngineResponseDto;
import com.grimoire.model.grimoire.typeTables.EngineTypeModel;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
@Table(name = "SISTEMAS")
@EqualsAndHashCode
public class EngineModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_CRIADOR", nullable = false)
    private UserModel owner;

    @Column(name = "NOME")
    private String name;

    @Column(name = "DESCRICAO")
    private String description;

    @Column(name = "ID_IMAGEM")
    private String pictureId;

    @ManyToOne
    @JoinColumn(name="ID_TIPO_SISTEMA", nullable = false)
    private EngineTypeModel engineType;

    public EngineResponseDto toDto() {
        return EngineResponseDto.builder()
                .id(this.id)
                .idUser(this.owner.getId())
                .name(this.name)
                .description(this.description)
                .pictureId(this.pictureId)
                .typeSys(this.engineType.getDescription())
                .build();
    }
}
